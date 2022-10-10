package webapp.tier.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class HazelcastCacheService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String cachename = GetConfig.getResourceBundle("hazelcast.cache.name");

	public String putMapHazelcast(HazelcastInstance client) throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
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

	public List<String> getMapHazelcast(HazelcastInstance client) throws Exception {
		List<String> allmsg = new ArrayList<>();
		Map<String, String> map = client.getMap(cachename);
		String fullmsg = null;

		try {
			for (Entry<String, String> entry : map.entrySet()) {
				fullmsg = "Selected Msg: id: " + entry.getKey() + ", message: " + entry.getValue();
				logger.info(fullmsg);
				allmsg.add(fullmsg);
			}

			if (allmsg.isEmpty()) {
				fullmsg = "No Data";
				logger.info(fullmsg);
				allmsg.add(fullmsg);
			}

		} finally {
			client.shutdown();
		}
		return allmsg;
	}
}
