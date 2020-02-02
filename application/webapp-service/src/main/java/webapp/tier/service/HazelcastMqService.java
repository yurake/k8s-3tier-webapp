package webapp.tier.service;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import webapp.tier.constant.EnumService;
import webapp.tier.util.CreateId;

public class HazelcastMqService {
	Logger logger = LoggerFactory.getLogger(HazelcastMqService.class);
	private static String message = EnumService.common_message.getString();
	private static String queuename = EnumService.hazelcast_queue_name.getString();
	private static String topicname = EnumService.hazelcast_topicname_name.getString();
	private static String splitkey = EnumService.hazelcast_split_key.getString();

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
			logger.info(fullmsg);
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
				logger.info(fullmsg);
				return fullmsg;
			}

			String jmsbody = resp.toString();
			String[] body = jmsbody.split(splitkey, 0);
			fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
			logger.info(fullmsg);

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
			logger.info(fullmsg);
		} finally {
			client.shutdown();
		}
		return fullmsg;
	}
}
