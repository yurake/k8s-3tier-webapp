package webapp.tier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.constant.EnumService;
import webapp.tier.util.CreateId;

public class HazelcastCacheService {
	private static Logger logger = LoggerFactory.getLogger(HazelcastCacheService.class);
	private static String message = EnumService.common_message.getString();
	private static String cachename = EnumService.hazelcast_cache_name.getString();

	public String putMapHazelcast() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		HazelcastInstance client = ConnectHazelcast.getInstance();
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
		HazelcastInstance client = ConnectHazelcast.getInstance();
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
