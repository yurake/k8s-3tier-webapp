package batch;

import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import batch.util.GetConfig;

public class GetMq implements Runnable {
	private static String queuename = GetConfig.getResourceBundle("batch.queue.name");
	private static String username = GetConfig.getResourceBundle("jms.username");
	private static String password = GetConfig.getResourceBundle("jms.password");
	private static String host = GetConfig.getResourceBundle("jms.host");
	private static String vhost = GetConfig.getResourceBundle("jms.vhost");
	private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

	@Override
	public void run() {
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

			DefaultConsumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws UnsupportedEncodingException {
					String jmsbody = new String(body, "UTF-8");
				    String[] value = jmsbody.split(splitkey, 0);
				    String id = value[0];
				    String message = value[1];
					System.out.println("Received: id: " + id + ", msg:" + message);

					InsertDb insdb = new InsertDb();
					System.out.println("insertMsg: id: " + id + ", msg:" + message);
					insdb.insertMsg(id, message);
				}
			};
			channel.basicConsume(queuename, true, consumer);
			System.out.println("Waiting for messages as Consumer...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
