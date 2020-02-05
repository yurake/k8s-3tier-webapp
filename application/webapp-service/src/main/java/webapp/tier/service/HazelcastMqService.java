package webapp.tier.service;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import webapp.tier.util.CreateId;

public class HazelcastMqService {

	private static final Logger  LOG = Logger.getLogger(HazelcastMqService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("hazelcast.queue.name", String.class);
	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topicname.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	public String putQueueHazelcast() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		HazelcastInstance client = ConnectHazelcast.getInstance();
		BlockingQueue<Object> queue = client.getQueue(queuename);

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		String body = buf.toString();

		try {
			queue.put(body);
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		} finally {
			client.shutdown();
		}
		return fullmsg;
	}

	public String getQueueHazelcast() throws Exception {
		HazelcastInstance client = ConnectHazelcast.getInstance();
		BlockingQueue<String> queue = client.getQueue(queuename);
		String fullmsg = null;

		try {
			Object resp = queue.poll();

			if (resp.toString().isEmpty()) {
				fullmsg = "No Data";
				LOG.info(fullmsg);
				return fullmsg;
			}

			String jmsbody = resp.toString();
			String[] body = jmsbody.split(splitkey, 0);
			fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
			LOG.info(fullmsg);

		} finally {
			client.shutdown();
		}
		return fullmsg;
	}

	public String publishHazelcast() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		HazelcastInstance client = ConnectHazelcast.getInstance();
		ITopic<Object> topic = client.getTopic(topicname);

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(message);
		String body = buf.toString();

		try {
			topic.publish(body);
			fullmsg = "Publish id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		} finally {
			client.shutdown();
		}
		return fullmsg;
	}
}
