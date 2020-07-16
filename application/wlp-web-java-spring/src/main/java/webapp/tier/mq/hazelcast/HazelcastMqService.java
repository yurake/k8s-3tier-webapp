package webapp.tier.mq.hazelcast;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import webapp.tier.cache.memcached.GetMemcached;
import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class HazelcastMqService {
	Logger logger = LoggerFactory.getLogger(GetMemcached.class);
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String pubmessage = GetConfig.getResourceBundle("hazelcast.publisher.message");
	private static String queuename = GetConfig.getResourceBundle("hazelcast.queue.name");
	private static String topicname = GetConfig.getResourceBundle("hazelcast.topic.name");
	private static String splitkey = GetConfig.getResourceBundle("hazelcast.split.key");

	public String putQueueHazelcast(HazelcastInstance client) throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
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

	public String getQueueHazelcast(HazelcastInstance client) throws Exception {
		BlockingQueue<String> queue = client.getQueue(queuename);
		String fullmsg = null;

		try {
			Object resp = queue.poll();

			if (StringUtils.isEmpty(resp)) {
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

	public String publishHazelcast(HazelcastInstance client) throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
		ITopic<Object> topic = client.getTopic(topicname);

		StringBuilder buf = new StringBuilder();
		buf.append(id);
		buf.append(splitkey);
		buf.append(pubmessage);
		String body = buf.toString();

		try {
			topic.publish(body);
			fullmsg = "Publish id: " + id + ", msg: " + pubmessage;
			logger.info(fullmsg);
		} finally {
			client.shutdown();
		}
		return fullmsg;
	}
}
