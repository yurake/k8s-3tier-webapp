package webapp.tier.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import io.smallrye.reactive.messaging.annotations.Blocking;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.RabbitmqSocket;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitmqService {
	
	@Channel("message") 
	Emitter<String> messageEmitter; 

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private static String message = ConfigProvider.getConfig().getValue("common.message",
			String.class);
	private static String queuename = ConfigProvider.getConfig().getValue(
			"rabbitmq.queue.name",
			String.class);
	private static String exchangename = ConfigProvider.getConfig()
			.getValue("rabbitmq.exchange.name", String.class);
	private static String routingkey = ConfigProvider.getConfig().getValue(
			"rabbitmq.exchange.routingkey",
			String.class);
	private static String username = ConfigProvider.getConfig().getValue(
			"rabbitmq.username",
			String.class);
	private static String password = ConfigProvider.getConfig().getValue(
			"rabbitmq.password",
			String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host",
			String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost",
			String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue(
			"rabbitmq.split.key",
			String.class);
	
	RabbitmqSocket rmqsock = new RabbitmqSocket();

	public Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	public MsgBean putMsg(Connection conn)
			throws NoSuchAlgorithmException, IOException, TimeoutException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Channel channel = conn.createChannel()) {
			channel.basicPublish("", queuename, null, body.getBytes());
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;

	}

	public MsgBean getMsg(Connection conn) throws IOException, TimeoutException {
		MsgBean msgbean = null;

		try (Channel channel = conn.createChannel()) {
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			GetResponse resp = channel.basicGet(queuename, true);
			if (resp == null) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = MsgUtils.splitBody(
						new String(resp.getBody(), StandardCharsets.UTF_8),
						splitkey);
				msgbean.setFullmsg("Get");
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}
	
	public MsgBean publishMsg(Connection conn) throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		messageEmitter.send(MsgUtils.createBody(msgbean, splitkey));
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Incoming("message")
	@Blocking
	public void consume(String message) {
		MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		
		rmqsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
		msgbean.setFullmsg("Broadcasted");
		logger.log(Level.INFO, msgbean.getFullmsg());
	}
}
