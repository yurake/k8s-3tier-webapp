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

import servlet.util.CreateId;
import servlet.util.GetConfig;

public class PutMqBatch extends HttpServlet {
    private static String queuename = GetConfig.getResourceBundle("batch.queue.name");
    private static String username = GetConfig.getResourceBundle("jms.username");
    private static String password = GetConfig.getResourceBundle("jms.password");
    private static String host = GetConfig.getResourceBundle("jms.host");
    private static String vhost = GetConfig.getResourceBundle("jms.vhost");
    private static String message = GetConfig.getResourceBundle("batch.message");
    private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Put MQ");
	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setUsername(username);
	connectionFactory.setPassword(password);
	connectionFactory.setHost(host);
	connectionFactory.setVirtualHost(vhost);

	try {
	    Connection connection = connectionFactory.newConnection();
	    Channel channel = connection.createChannel();

	    String id = String.valueOf(CreateId.createid());
	    out.println("id: " + id);
	    out.println("msg: " + message);

	    StringBuilder buf = new StringBuilder();
	    buf.append(id);
	    buf.append(splitkey);
	    buf.append(message);
	    String body = buf.toString();

	    channel.basicPublish("", queuename, null, body.getBytes());

	    channel.close();
	    connection.close();

	    System.out.println("Set: id: " + id + ", msg:" + message);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
