package webapp.tier.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class RabbitmqService {
	Logger logger = LoggerFactory.getLogger(RabbitmqService.class);
	private static String queuename = GetConfig.getResourceBundle("rabbitmq.queue.name");
	private static String username = GetConfig.getResourceBundle("rabbitmq.username");
	private static String password = GetConfig.getResourceBundle("rabbitmq.password");
	private static String host = GetConfig.getResourceBundle("rabbitmq.host");
	private static String vhost = GetConfig.getResourceBundle("rabbitmq.vhost");
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String splitkey = GetConfig.getResourceBundle("rabbitmq.split.key");

	public Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	public boolean isActive() {
		boolean status = false;
		try (Connection connection = getConnection()) {
			status = true;
		} catch (Exception e) {
			logger.error("Connect Error.", e);
		}
		return status;
	}

	public String put() throws IOException, TimeoutException {
		String fullmsg = "Error";
		Connection conn = getConnection();
		try (Channel channel = conn.createChannel()) {
			String id = String.valueOf(CreateId.createid());
			StringBuilder buf = new StringBuilder();
			buf.append(id);
			buf.append(splitkey);
			buf.append(message);
			String body = buf.toString();
			channel.basicPublish("", queuename, null, body.getBytes());
			fullmsg = "Set id: " + id + ", msg:" + message;
		}
		logger.info(fullmsg);
		return fullmsg;
	}

	public String get() throws IOException, TimeoutException {
		String fullmsg = "Error";
		Connection conn = getConnection();
		try (Channel channel = conn.createChannel()) {
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);
			GetResponse resp = channel.basicGet(queuename, true);
			if (StringUtils.isEmpty(resp)) {
				fullmsg = "No Data";
			} else {
				String jmsbody = new String(resp.getBody(), "UTF-8");
				String[] body = jmsbody.split(splitkey, 0);
				fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
			}
		}
		logger.info(fullmsg);
		return fullmsg;
	}

	public String publish() throws IOException, TimeoutException {
		String fullmsg = "Error";
		Connection conn = getConnection();
		try (Channel channel = conn.createChannel()) {
			String id = String.valueOf(CreateId.createid());
			StringBuilder buf = new StringBuilder();
			buf.append(id);
			buf.append(splitkey);
			buf.append(message);
			String body = buf.toString();
			channel.basicPublish("", queuename, null, body.getBytes());
			fullmsg = "Publish id: " + id + ", msg: " + message;
		}
		logger.info(fullmsg);
		return fullmsg;
	}
}
