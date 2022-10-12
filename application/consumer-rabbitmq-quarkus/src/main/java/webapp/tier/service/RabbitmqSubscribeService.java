package webapp.tier.service;

import java.io.IOException;
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

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class RabbitmqSubscribeService implements Runnable {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

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

	protected RabbitmqDeliverSubscriber createRabbitmqDeliverSubscriber(Channel channel) {
		return new RabbitmqDeliverSubscriber(channel);
	}

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.log(Level.INFO, "Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.log(Level.INFO, "Subscribe is stopping...");
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

	public Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	protected void subscribeRabbitmq(Channel channel,
			RabbitmqDeliverSubscriber subscriber)
			throws IOException, InterruptedException {
		channel.exchangeDeclare(exchangename, "direct", true);
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangename, routingkey);
		channel.basicConsume(queueName, false, "myConsumerTag", subscriber);

		while (isEnableReceived) {
			TimeUnit.MINUTES.sleep(10L);
		}
	}

	@Override
	public void run() {
		try (Connection conn = getConnection();
				Channel channel = conn.createChannel()) {
			subscribeRabbitmq(channel, createRabbitmqDeliverSubscriber(channel));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Subscribe Errorr.", e);
		}
	}
}
