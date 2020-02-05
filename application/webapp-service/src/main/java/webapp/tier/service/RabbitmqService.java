package webapp.tier.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.util.CreateId;

public class RabbitmqService {

	private static final Logger LOG = Logger.getLogger(RabbitmqService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("rabbitmq.queue.name", String.class);
	private static String pubsubqueuename = ConfigProvider.getConfig().getValue("rabbitmq.pubsub.queue.name",
			String.class);
	private static String username = ConfigProvider.getConfig().getValue("rabbitmq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("rabbitmq.password", String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host", String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);

	public String getMessageQueue() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		String fullmsg = null;

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		boolean durable = true;
		channel.queueDeclare(queuename, durable, false, false, null);

		GetResponse resp = channel.basicGet(queuename, true);
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}

		if (resp.toString().isEmpty()) {
			return "No Data";
		}

		String jmsbody = new String(resp.getBody(), "UTF-8");
		String[] body = jmsbody.split(splitkey, 0);
		fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
		LOG.info(fullmsg);

		return fullmsg;
	}

	public String putMessageQueue() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		String fullmsg = null;

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		String body = buf.toString();

		channel.basicPublish("", queuename, null, body.getBytes());

		fullmsg = "Set id: " + id + ", msg:" + message;
		LOG.info(fullmsg);

		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}

		return fullmsg;
	}

	public String putMessageQueueConsumer() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		String fullmsg = null;

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		String id = String.valueOf(CreateId.createid());

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		String body = buf.toString();

		channel.basicPublish("", pubsubqueuename, null, body.getBytes());

		fullmsg = "Publish id: " + id + ", msg: " + message;
		LOG.info(fullmsg);

		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}

		return fullmsg;
	}
}
