package webapp.tier.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

public class HazelcastMqService implements Messaging {

	private static final Logger LOG = Logger.getLogger(HazelcastMqService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("hazelcast.queue.name", String.class);
	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topicname.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	@Override
	public String putMsg() throws RuntimeException, NoSuchAlgorithmException {
		HazelcastInstance client = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		LOG.fine("start put");

		try {
			client = ConnectHazelcast.getInstance();
			BlockingQueue<Object> queue = client.getQueue(queuename);
			queue.put(body);
		} catch (IOException | IllegalStateException | InterruptedException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
			throw new RuntimeException("Put Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		LOG.fine("end put");
		return msgbean.getFullmsg();
	}

	@Override
	public String getMsg() throws RuntimeException {
		MsgBeanUtils msgbean = new MsgBeanUtils();
		HazelcastInstance client = null;

		try {
			client = ConnectHazelcast.getInstance();
			BlockingQueue<String> queue = client.getQueue(queuename);
			Object resp = queue.poll();

			if (Objects.isNull(resp) || resp.toString().isEmpty()) {
				msgbean.setFullmsg("No Data");
			} else {
				String jmsbody = resp.toString();
				msgbean.setFullmsgWithType(msgbean.splitBody(jmsbody, splitkey), "Get");
			}
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String publishMsg() throws RuntimeException, NoSuchAlgorithmException {
		HazelcastInstance client = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		LOG.fine("start publish");

		try {
			client = ConnectHazelcast.getInstance();
			ITopic<Object> topic = client.getTopic(topicname);
			topic.publish(body);
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		LOG.fine("end publish");
		return msgbean.getFullmsg();
	}
}
