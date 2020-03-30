package webapp.tier.service;

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

	private Connection getConnection() throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory.newConnection();
	}

	private Channel getChannel(Connection con) throws Exception {
		return con.createChannel();
	}

	private void closeConnectionChannel(Connection connection, Channel channel) throws Exception {
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	@Override
	public String putMsg() throws Exception {
		Connection connection = null;
		Channel channel = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			connection = getConnection();
			channel = getChannel(connection);
			channel.basicPublish("", queuename, null, body.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		} finally {
			closeConnectionChannel(connection, channel);
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();

	}

	@Override
	public String getMsg() throws Exception {
		Connection connection = null;
		Channel channel = null;
		MsgBeanUtils msgbean = new MsgBeanUtils();

		try {
			connection = getConnection();
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


		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			closeConnectionChannel(connection, channel);
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String publishMsg() throws Exception {
		Connection connection = null;
		Channel channel = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			connection = getConnection();
			channel = getChannel(connection);
			channel.basicPublish("", pubsubqueuename, null, body.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		} finally {
			closeConnectionChannel(connection, channel);
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}


	public boolean isActive() {
		Connection connection = null;
		Channel channel = null;
		boolean status = false;
		try {
			connection = getConnection();
			channel = getChannel(connection);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnectionChannel(connection, channel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}
}
