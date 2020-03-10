package webapp.tier.service;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
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
	public String putMsg() throws Exception {
		HazelcastInstance client = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			client = ConnectHazelcast.getInstance();
			BlockingQueue<Object> queue = client.getQueue(queuename);
			queue.put(body);

			msgbean.setFullmsgWithType(msgbean, "Put");
			LOG.info(msgbean.getFullmsg());
			return msgbean.getFullmsg();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	@Override
	public String getMsg() throws Exception {
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

			LOG.info(msgbean.getFullmsg());
			return msgbean.getFullmsg();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	@Override
	public String publishMsg() throws Exception {
		HazelcastInstance client = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);

		try {
			client = ConnectHazelcast.getInstance();
			ITopic<Object> topic = client.getTopic(topicname);
			topic.publish(body);

			msgbean.setFullmsgWithType(msgbean, "Put");
			LOG.info(msgbean.getFullmsg());
			return msgbean.getFullmsg();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}
}
