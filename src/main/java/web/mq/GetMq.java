package web.mq;

import javax.servlet.http.HttpServlet;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import web.util.GetConfig;

public class GetMq extends HttpServlet {
	private static String queuename = GetConfig.getResourceBundle("queue.name");
	private static String username = GetConfig.getResourceBundle("jms.username");
	private static String password = GetConfig.getResourceBundle("jms.password");
	private static String host = GetConfig.getResourceBundle("jms.host");
	private static String vhost = GetConfig.getResourceBundle("jms.vhost");
	private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

	public String getMessageQueue() {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		String fullmsg = null;

		try {
			Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel();
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			GetResponse resp = channel.basicGet(queuename, true);
			String jmsbody = new String(resp.getBody(), "UTF-8");
			String[] body = jmsbody.split(splitkey, 0);
			fullmsg = "Received id: " + body[0]+ ", msg: " + body[1];
			System.out.println(fullmsg);

			channel.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fullmsg;
	}
}
