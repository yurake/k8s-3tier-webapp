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

@SuppressWarnings("serial")
public class PutMq extends HttpServlet {
    private final static String QUEUE_NAME = "queue1";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	PrintWriter out = response.getWriter();
	out.println("Put MQ");
	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setUsername("devtest1");
	connectionFactory.setPassword("devtest1");
	connectionFactory.setHost("rabbitmq");
	connectionFactory.setVirtualHost("vhost1");
	try {
	    Connection connection = connectionFactory.newConnection();
	    Channel channel = connection.createChannel();
	    String message = "Hello oss-3tier-webapp!";
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	    channel.close();
	    connection.close();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}
