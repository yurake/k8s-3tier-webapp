package webapp.tier.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.constant.EnumService;
import webapp.tier.util.CreateId;

public class RabbitmqService {

	private static final Logger LOG = Logger.getLogger(RabbitmqService.class.getSimpleName());
	private static String message = EnumService.common_message.getString();
	private static String queuename = EnumService.rabbitmq_queue_name.getString();
	private static String pubsubqueuename = EnumService.rabbitmq_pubsub_queue_name.getString();
	private static String username = EnumService.rabbitmq_username.getString();
	private static String password = EnumService.rabbitmq_password.getString();
	private static String host = EnumService.rabbitmq_host.getString();
	private static String vhost = EnumService.rabbitmq_vhost.getString();
	private static String splitkey = EnumService.rabbitmq_split_key.getString();

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
		channel.close();
		connection.close();

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

		channel.close();
		connection.close();

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

		channel.close();
		connection.close();

		return fullmsg;
	}
}
