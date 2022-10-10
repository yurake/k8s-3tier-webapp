package webapp.tier.mq;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.util.CreateId;

public class RabbitmqService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${common.message}")
	private String message;

	@Value("${rabbitmq.queue.name}")
	private String queuename;

	@Value("${rabbitmq.queue.batch.name}")
	private String batchqueuename;

	@Value("${rabbitmq.username}")
	private String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.host}")
	private String host;

	@Value("${rabbitmq.vhost}")
	private String vhost;

	@Value("${rabbitmq.split.key}")
	private String splitkey;

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

	private String getBody(String id) {
		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		return buf.toString();
	}

	private String basicPublish(String queue, String type) throws IOException, TimeoutException, NoSuchAlgorithmException {
		String fullmsg = "Error";
		Connection conn = getConnection();
		try (Channel channel = conn.createChannel()) {
			String id = String.valueOf(CreateId.createid());
			String body = getBody(id);
			channel.basicPublish("", queue, null, body.getBytes());
			fullmsg = type + " id: " + id + ", msg: " + message;
		}
		logger.info(fullmsg);
		return fullmsg;
	}

	public String put() throws IOException, TimeoutException, NoSuchAlgorithmException {
		return basicPublish(queuename, "Set");
	}

	public String get() throws IOException, TimeoutException {
		String fullmsg = "Error";
		Connection conn = getConnection();
		try (Channel channel = conn.createChannel()) {
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);
			GetResponse resp = channel.basicGet(queuename, true);

			if (Objects.isNull(resp) || resp.toString().isEmpty()) {
				fullmsg = "No Data";
			} else {
				String jmsbody = new String(resp.getBody(), "UTF-8");
				String[] body = jmsbody.split(splitkey, 0);
				fullmsg = "Get id: " + body[0] + ", msg: " + body[1];
			}
		}
		logger.info(fullmsg);
		return fullmsg;
	}

	public String publish() throws IOException, TimeoutException, NoSuchAlgorithmException {
		return basicPublish(batchqueuename, "Publish");
	}
}
