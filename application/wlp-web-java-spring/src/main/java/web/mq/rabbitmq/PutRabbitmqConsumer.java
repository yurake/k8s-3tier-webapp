package web.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import web.util.CreateId;
import web.util.GetConfig;

public class PutRabbitmqConsumer extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(PutRabbitmqConsumer.class);
	private static String queuename = GetConfig.getResourceBundle("rabbitmq.batch.queue.name");
	private static String username = GetConfig.getResourceBundle("rabbitmq.username");
	private static String password = GetConfig.getResourceBundle("rabbitmq.password");
	private static String host = GetConfig.getResourceBundle("rabbitmq.host");
	private static String vhost = GetConfig.getResourceBundle("rabbitmq.vhost");
	private static String message = GetConfig.getResourceBundle("rabbitmq.consumer.message");
	private static String splitkey = GetConfig.getResourceBundle("rabbitmq.split.key");

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

		channel.basicPublish("", queuename, null, body.getBytes());

		fullmsg = "Publish id: " + id + ", msg: " + message;
		logger.info(fullmsg);

		channel.close();
		connection.close();

		return fullmsg;
	}
}
