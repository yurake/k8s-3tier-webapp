package webapp.tier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.util.CreateId;

public class HazelcastCacheService {

	private static Logger LOG = Logger.getLogger(HazelcastCacheService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String cachename = ConfigProvider.getConfig().getValue("hazelcast.cache.name", String.class);

	public String putMapHazelcast() throws Exception {
		String fullmsg = "Error";
		String id = String.valueOf(CreateId.createid());
		HazelcastInstance client = null;

		try {
			client = ConnectHazelcast.getInstance();
			Map<String, String> map = client.getMap(cachename);
			map.put(id, message);
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return fullmsg;
	}

	public List<String> getMapHazelcast() throws Exception {
		List<String> allmsg = new ArrayList<>();
		String fullmsg = "Error";
		HazelcastInstance client = null;

		try {
			client = ConnectHazelcast.getInstance();
			Map<String, String> map = client.getMap(cachename);
			for (Entry<String, String> entry : map.entrySet()) {
				fullmsg = "Selected Msg: id: " + entry.getKey() + ", message: " + entry.getValue();
				LOG.info(fullmsg);
				allmsg.add(fullmsg);
			}

			if (allmsg.isEmpty()) {
				fullmsg = "No Data";
				LOG.info(fullmsg);
				allmsg.add(fullmsg);
			}

		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return allmsg;
	}
}
