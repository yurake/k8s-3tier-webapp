package webapp.tier.mq;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import webapp.tier.util.CreateId;

@Service
public class HazelcastMqService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${common.message}")
	private String message;

	@Value("${hazelcast.queue.name}")
	private String queuename;

	@Value("${hazelcast.topic.name}")
	private String topicname;

	@Value("${hazelcast.split.key}")
	private String splitkey;

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
			 String resp = queue.poll();

				if (Objects.isNull(resp) || resp.isEmpty()) {
				fullmsg = "No Data";
				logger.info(fullmsg);
				return fullmsg;
			}

			String[] body = resp.split(splitkey, 0);
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
