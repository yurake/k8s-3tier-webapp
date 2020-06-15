package webapp.tier.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitMqService implements Runnable {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private static final Logger LOG = Logger.getLogger(RabbitMqService.class.getSimpleName());
	private static String username = ConfigProvider.getConfig().getValue("rabbitmq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("rabbitmq.password", String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host", String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);
	private static String exchangename = ConfigProvider.getConfig().getValue("rabbitmq.exchange.name", String.class);
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("The application is stopping...");
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
	public void run() {
		Channel channel = null;
		try (Connection connection = getConnection()) {
			channel = connection.createChannel();

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
					LOG.info("Call: Random Publish");
					LOG.info(deliversvc.random());
				}
			};
			channel.basicConsume(queueName, true, consumer);

			while (isEnableReceived) {
				TimeUnit.MINUTES.sleep(10L);
			}

		} catch (IOException | TimeoutException | InterruptedException e) {
			LOG.log(Level.SEVERE, "Subscribe Errorr.", e);
		}
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


	public static void startReceived() {
		RabbitMqService.isEnableReceived = true;
	}

	public static void stopReceived() {
		RabbitMqService.isEnableReceived = false;
	}
}
