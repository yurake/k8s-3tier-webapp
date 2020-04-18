package webapp.tier.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class RabbitMqService implements Runnable {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private static final Logger LOG = Logger.getLogger(RabbitMqService.class.getSimpleName());
	private static String queuename = ConfigProvider.getConfig().getValue("rabbitmq.queue.name", String.class);
	private static String username = ConfigProvider.getConfig().getValue("rabbitmq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("rabbitmq.password", String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host", String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

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

	private Channel getChannel(Connection con) throws IOException {
		return con.createChannel();
	}

	private void closeChannel(Channel channel) throws IOException, TimeoutException {
		if (channel != null) {
			channel.close();
		}
	}

	@Override
	public void run() {
		Channel channel = null;
		try (Connection connection = getConnection()) {
			channel = getChannel(connection);
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			DefaultConsumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws UnsupportedEncodingException {

					MsgBeanUtils msgbean = new MsgBeanUtils();
					MsgBean bean = msgbean.splitBody(new String(body, "UTF-8"), splitkey);
					msgbean.setFullmsgWithType(bean, "Received");
					LOG.info(msgbean.getFullmsg());

					MysqlService mysqlsvc = new MysqlService();
					try {
						mysqlsvc.insertMsg(bean);
						LOG.info("Call: Random Publish");
						LOG.info(deliversvc.random());
					} catch (SQLException | NoSuchAlgorithmException e) {
						LOG.log(Level.SEVERE, "Insert Errorr.", e);
					}
				}
			};
			channel.basicConsume(queuename, true, consumer);
			LOG.info("Waiting for messages as Consumer...");

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Subscribe Errorr.", e);
		} finally {
			try {
				closeChannel(channel);
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Channel Close Error.", e);
			}
		}
	}

	public boolean isActive() {
		Channel channel = null;
		boolean status = false;
		try (Connection connection = getConnection()) {
			channel = getChannel(connection);
			status = true;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		} finally {
			try {
				closeChannel(channel);
			} catch (IOException | TimeoutException e) {
				LOG.log(Level.SEVERE, "Channel Close Error.", e);
			}
		}
		return status;
	}
}
