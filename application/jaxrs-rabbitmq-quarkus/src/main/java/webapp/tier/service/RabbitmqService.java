package webapp.tier.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

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

	@Override
	public MsgBean putMsg() throws RuntimeException, NoSuchAlgorithmException, IOException, TimeoutException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Connection connection = getConnection()) {
			connection.createChannel().basicPublish("", queuename, null, body.getBytes());

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
			throw new RuntimeException("Put Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;

	}

	@Override
	public MsgBean getMsg() throws RuntimeException, IOException, TimeoutException {
		MsgBean msgbean = null;

		try (Connection connection = getConnection()) {
			boolean durable = true;
			connection.createChannel().queueDeclare(queuename, durable, false, false, null);

			GetResponse resp = connection.createChannel().basicGet(queuename, true);
			if (resp == null) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = MsgUtils.splitBody(new String(resp.getBody(), "UTF-8"), splitkey);
				msgbean.setFullmsg("Get");
			}
		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public MsgBean publishMsg() throws Exception {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try (Connection connection = getConnection()) {
			connection.createChannel().basicPublish("", pubsubqueuename, null, body.getBytes());

		} catch (IOException | TimeoutException e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}


	public boolean isActive() {
		boolean status = false;
		try (Connection connection = getConnection()) {
			status = true;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		}
		return status;
	}
}
