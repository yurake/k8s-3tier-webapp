package webapp.tier.service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class RabbitMqService implements Runnable {

	Logger logger = LoggerFactory.getLogger(RabbitMqService.class);
	private static String queuename = ConfigProvider.getConfig().getValue("rabbitmq.queue.name", String.class);
	private static String username = ConfigProvider.getConfig().getValue("rabbitmq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("rabbitmq.password", String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host", String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	private static Connection connection = null;
	private static Channel channel = null;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.info("The application is stopping...");
	}

	private Channel getJmsChannel() throws Exception {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);

		if (connection == null) {
			connection = connectionFactory.newConnection();
		}
		if (channel == null) {
			channel = connection.createChannel();
		}
		return channel;
	}

	@Override
	public void run() {
		try {
			Channel channel = getJmsChannel();
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			DefaultConsumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws UnsupportedEncodingException {
					String jmsbody = new String(body, "UTF-8");
					String[] value = jmsbody.split(splitkey, 0);
					String id = value[0];
					String message = value[1];
					logger.info("Received: id: " + id + ", msg:" + message);

					MysqlService mysqlsvc = new MysqlService();
					mysqlsvc.insertMsg(value);
				}
			};
			channel.basicConsume(queuename, true, consumer);
			logger.info("Waiting for messages as Consumer...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isActive() {
		boolean status = false;
		try {
			getJmsChannel();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
