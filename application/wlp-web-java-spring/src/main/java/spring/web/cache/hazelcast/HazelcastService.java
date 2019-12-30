package spring.web.cache.hazelcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.hazelcast.config.Config;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import spring.web.cache.memcached.GetMemcached;
import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class HazelcastService {
	Logger logger = LoggerFactory.getLogger(GetMemcached.class);
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String cachename = GetConfig.getResourceBundle("hazelcast.cache.name");
	private static String queuename = GetConfig.getResourceBundle("hazelcast.queue.name");
	private static String splitkey = GetConfig.getResourceBundle("jms.split.key");

	public static HazelcastInstance getInstance() throws Exception {

		Config config = new Config();
		RestApiConfig restApiConfig = new RestApiConfig().setEnabled(true).disableAllGroups()
				.enableGroups(RestEndpointGroup.DATA);
		config.getNetworkConfig().setRestApiConfig(restApiConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

	public String putMapHazelcast() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		HazelcastInstance client = HazelcastService.getInstance();
		Map<String, String> map = client.getMap(cachename);

		try {
			map.put(id, message);
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} finally {
			client.shutdown();
		}
		return fullmsg;
	}

	public List<String> getMapHazelcast() throws Exception {
		List<String> allmsg = new ArrayList<>();
		HazelcastInstance client = HazelcastService.getInstance();
		Map<String, String> map = client.getMap(cachename);

		try {
			for (Entry<String, String> entry : map.entrySet()) {
				String fullmsg = "Selected Msg: id: " + entry.getKey() + ", message: " + entry.getValue();
				logger.info(fullmsg);
				allmsg.add(fullmsg);
			}

			if (allmsg.isEmpty()) {
				allmsg.add("No Data");
			}

		} finally {
			client.shutdown();
		}
		return allmsg;
	}

	public String putQueueHazelcast() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		HazelcastInstance client = HazelcastService.getInstance();
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
		HazelcastInstance client = HazelcastService.getInstance();
		BlockingQueue<Object> queue = client.getQueue(queuename);
		String fullmsg = null;

		try {
			Object resp = queue.poll();

			if (StringUtils.isEmpty(resp)) {
				return "No Data";
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

	public String publishHazelcast() {
		return null;
	}
}
