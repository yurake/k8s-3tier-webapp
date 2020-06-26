package webapp.tier.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Messaging;
import webapp.tier.service.socket.RabbitmqSocket;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitmqService implements Messaging, Runnable {

	@Inject
	RabbitmqSocket rmqsock;

	private static final Logger LOG = Logger.getLogger(RabbitmqService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("rabbitmq.queue.name", String.class);
	private static String exchangename = ConfigProvider.getConfig().getValue("rabbitmq.exchange.name", String.class);
	private static String username = ConfigProvider.getConfig().getValue("rabbitmq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("rabbitmq.password", String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host", String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("Subscribe is stopping...");
	}

	private Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	@Override
	public MsgBean putMsg() throws RuntimeException, NoSuchAlgorithmException, IOException, TimeoutException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Connection connection = getConnection();
				Channel channel = connection.createChannel()) {
			channel.basicPublish("", queuename, null, body.getBytes());

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
			throw new RuntimeException("Put Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;

	}

	@Override
	public MsgBean getMsg() throws RuntimeException, IOException, TimeoutException {
		MsgBean msgbean = null;

		try (Connection connection = getConnection();
				Channel channel = connection.createChannel()) {
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			GetResponse resp = channel.basicGet(queuename, true);
			if (resp == null) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = MsgUtils.splitBody(new String(resp.getBody(), "UTF-8"), splitkey);
				msgbean.setFullmsg("Get");
			}
		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean publishMsg() throws Exception {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Connection connection = getConnection();
				Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(exchangename, "fanout");
			channel.basicPublish(exchangename, "", null, body.getBytes("UTF-8"));

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		}

		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	public boolean isActive() {
		boolean status = false;
		try (Connection connection = getConnection()) {
			status = true;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		}
		return status;
	}

	@Override
	public void run() {
		try (Connection connection = getConnection();
				Channel channel = connection.createChannel()) {

			channel.exchangeDeclare(exchangename, "fanout");
			String queueName = channel.queueDeclare().getQueue();
			LOG.log(Level.INFO, "Create queue: " + queueName);
			channel.queueBind(queueName, exchangename, "");

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws UnsupportedEncodingException {
					MsgBean msgbean = MsgUtils.splitBody(new String(body, "UTF-8"), splitkey);
					msgbean.setFullmsg("Received");
					LOG.info(msgbean.getFullmsg());
					rmqsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
					msgbean.setFullmsg("Broadcast");
					LOG.log(Level.INFO, msgbean.getFullmsg());
				}
			};
			channel.basicConsume(queueName, true, consumer);

			while (isEnableReceived) {
				TimeUnit.MINUTES.sleep(10L);
			}

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Subscribe Errorr.", e);
		} catch (InterruptedException e) {
		    LOG.log(Level.WARNING, "Interrupted!", e);
		    Thread.currentThread().interrupt();
		}
	}

	public static void startReceived() {
		RabbitmqService.isEnableReceived = true;
	}

	public static void stopReceived() {
		RabbitmqService.isEnableReceived = false;
	}
}
