package webapp.tier.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.util.GetConfig;

public class GetRabbitmq extends HttpServlet {
	Logger logger = LoggerFactory.getLogger(GetRabbitmq.class);
	private static String queuename = GetConfig.getResourceBundle("rabbitmq.queue.name");
	private static String username = GetConfig.getResourceBundle("rabbitmq.username");
	private static String password = GetConfig.getResourceBundle("rabbitmq.password");
	private static String host = GetConfig.getResourceBundle("rabbitmq.host");
	private static String vhost = GetConfig.getResourceBundle("rabbitmq.vhost");
	private static String splitkey = GetConfig.getResourceBundle("rabbitmq.split.key");

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

		if (StringUtils.isEmpty(resp)) {
			return "No Data";
		}

		String jmsbody = new String(resp.getBody(), "UTF-8");
		String[] body = jmsbody.split(splitkey, 0);
		fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
		logger.info(fullmsg);

		return fullmsg;
	}
}
