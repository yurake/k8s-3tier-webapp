package webapp.tier.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitmqService implements Runnable {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

	private static String message = ConfigProvider.getConfig().getValue("common.message",
			String.class);
	private static String queuename = ConfigProvider.getConfig().getValue(
			"rabbitmq.queue.name",
			String.class);
	private static String exchangename = ConfigProvider.getConfig()
			.getValue("rabbitmq.exchange.name", String.class);
	private static String routingkey = ConfigProvider.getConfig().getValue(
			"rabbitmq.exchange.routingkey",
			String.class);
	private static String username = ConfigProvider.getConfig().getValue(
			"rabbitmq.username",
			String.class);
	private static String password = ConfigProvider.getConfig().getValue(
			"rabbitmq.password",
			String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host",
			String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost",
			String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue(
			"rabbitmq.split.key",
			String.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.log(Level.INFO, "Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.log(Level.INFO, "Subscribe is stopping...");
	}

	public static void startReceived() {
		RabbitmqService.isEnableReceived = true;
	}

	public static void stopReceived() {
		RabbitmqService.isEnableReceived = false;
	}

	public Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	protected RabbitmqConsumer createRabbitmqConsumer(Channel channel) {
		return new RabbitmqConsumer(channel);
	}

	public MsgBean putMsg(Connection conn)
			throws NoSuchAlgorithmException, IOException, TimeoutException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Channel channel = conn.createChannel()) {
			channel.basicPublish("", queuename, null, body.getBytes());
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;

	}

	public MsgBean getMsg(Connection conn) throws IOException, TimeoutException {
		MsgBean msgbean = null;

		try (Channel channel = conn.createChannel()) {
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			GetResponse resp = channel.basicGet(queuename, true);
			if (resp == null) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = MsgUtils.splitBody(
						new String(resp.getBody(), StandardCharsets.UTF_8),
						splitkey);
				msgbean.setFullmsg("Get");
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean publishMsg(Connection conn)
			throws NoSuchAlgorithmException, IOException, TimeoutException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Channel channel = conn.createChannel()) {
			channel.exchangeDeclare(exchangename, "direct", true);
			channel.basicPublish(exchangename, routingkey, null,
					body.getBytes(StandardCharsets.UTF_8));
		}

		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public boolean isActive() {
		boolean status = false;
		try (Connection connection = getConnection()) {
			status = true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Connect Error.", e);
		}
		return status;
	}

	@Override
	public void run() {
		try (Connection conn = getConnection();
				Channel channel = conn.createChannel()) {
			subscribeRabbitmq(channel, createRabbitmqConsumer(channel));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Subscribe Errorr.", e);
		}
	}

	protected void subscribeRabbitmq(Channel channel, RabbitmqConsumer consumer)
			throws IOException, TimeoutException, InterruptedException {
		channel.exchangeDeclare(exchangename, "direct", true);
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangename, routingkey);
		channel.basicConsume(queueName, false, "myConsumerTag", consumer);

		while (isEnableReceived) {
			TimeUnit.MINUTES.sleep(10L);
		}
	}
}
