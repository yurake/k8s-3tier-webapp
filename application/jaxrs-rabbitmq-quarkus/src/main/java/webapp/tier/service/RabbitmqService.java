package webapp.tier.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

public class RabbitmqService implements Messaging {

	private static final Logger LOG = Logger.getLogger(RabbitmqService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("rabbitmq.queue.name", String.class);
	private static String pubsubqueuename = ConfigProvider.getConfig().getValue("rabbitmq.pubsub.queue.name",
			String.class);
	private static String username = ConfigProvider.getConfig().getValue("rabbitmq.username", String.class);
	private static String password = ConfigProvider.getConfig().getValue("rabbitmq.password", String.class);
	private static String host = ConfigProvider.getConfig().getValue("rabbitmq.host", String.class);
	private static String vhost = ConfigProvider.getConfig().getValue("rabbitmq.vhost", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("rabbitmq.split.key", String.class);

	private Connection getConnection() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	private Channel getChannel(Connection con) throws IOException {
		return con.createChannel();
	}

	private void closeChannel(Channel channel) throws IOException, TimeoutException {
		if (channel != null) {
			channel.close();
		}
	}

	@Override
	public String putMsg() throws RuntimeException, NoSuchAlgorithmException, IOException, TimeoutException {
		Channel channel = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try (Connection connection = getConnection()) {
			channel = getChannel(connection);
			channel.basicPublish("", queuename, null, body.getBytes());

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
			throw new RuntimeException("Put Error.");
		} finally {
			closeChannel(channel);
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();

	}

	@Override
	public String getMsg() throws RuntimeException, IOException, TimeoutException {
		Channel channel = null;
		MsgBeanUtils msgbean = new MsgBeanUtils();

		try (Connection connection = getConnection()) {
			channel = getChannel(connection);
			boolean durable = true;
			channel.queueDeclare(queuename, durable, false, false, null);

			GetResponse resp = channel.basicGet(queuename, true);
			if (resp == null) {
				msgbean.setFullmsg("No Data");
			} else {
				String jmsbody = new String(resp.getBody(), "UTF-8");
				msgbean.setFullmsgWithType(msgbean.splitBody(jmsbody, splitkey), "Get");
			}
		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		} finally {
			closeChannel(channel);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String publishMsg() throws Exception {
		Channel channel = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try (Connection connection = getConnection()) {
			channel = getChannel(connection);
			channel.basicPublish("", pubsubqueuename, null, body.getBytes());

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		} finally {
			closeChannel(channel);
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}


	public boolean isActive() {
		boolean status = false;
		try (Connection connection = getConnection()) {
			getChannel(connection);
			status = true;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		}
		return status;
	}
}
