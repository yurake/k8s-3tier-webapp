package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import servlet.util.GetConfig;

public class GetMq extends HttpServlet {
    private static String queuename = GetConfig.getResourceBundle("queue.name");
    private static String username = GetConfig.getResourceBundle("jms.username");
    private static String password = GetConfig.getResourceBundle("jms.password");
    private static String host = GetConfig.getResourceBundle("jms.host");
    private static String vhost = GetConfig.getResourceBundle("jms.vhost");
    private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Get MQ");
	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setUsername(username);
	connectionFactory.setPassword(password);
	connectionFactory.setHost(host);
	connectionFactory.setVirtualHost(vhost);

	try {
	    Connection connection = connectionFactory.newConnection();
	    Channel channel = connection.createChannel();
	    boolean durable = true;
	    channel.queueDeclare(queuename, durable, false, false, null);

	    /**
	     * 受信を常駐して監視する場合 Consumer consumer = new DefaultConsumer(channel) {
	     *
	     * @Override public void handleDelivery(String consumerTag, Envelope envelope,
	     *           AMQP.BasicProperties properties, byte[] body) throws IOException {
	     *           String message = new String(body, "UTF-8"); out.println("Received
	     *           '" + message + "'"); } }; channel.basicConsume(QUEUE_NAME, true,
	     *           consumer);
	     */

	    /**
	     * 1度受信する場合
	     */
	    GetResponse resp = channel.basicGet(queuename, true);
	    String jmsbody = new String(resp.getBody(), "UTF-8");
	    String[] body = jmsbody.split(splitkey, 0);
	    String id = body[0];
	    String message = body[1];
	    out.println("id: " + id);
	    out.println("msg: " + message);
	    System.out.println("Received: id: " + id + ", msg:" + message);

	    channel.close();
	    connection.close();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
