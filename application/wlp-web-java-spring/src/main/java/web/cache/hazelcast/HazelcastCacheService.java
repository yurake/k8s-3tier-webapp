package web.cache.hazelcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import web.cache.memcached.GetMemcached;
import web.util.CreateId;
import web.util.GetConfig;

public class HazelcastCacheService {
	Logger logger = LoggerFactory.getLogger(GetMemcached.class);
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String cachename = GetConfig.getResourceBundle("hazelcast.cache.name");
	private static HazelcastInstance instance = null;

	synchronized static public HazelcastInstance getInstance() throws Exception {
		if (instance == null) {
			instance = createInstance();
		}
		return instance;
	}

	public static HazelcastInstance createInstance() throws Exception {
		Config config = new Config();
		RestApiConfig restApiConfig = new RestApiConfig().setEnabled(true).disableAllGroups()
				.enableGroups(RestEndpointGroup.DATA);
		config.getNetworkConfig().setRestApiConfig(restApiConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

	public String putMapHazelcast() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());

		HazelcastInstance client = HazelcastCacheService.getInstance();
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
		HazelcastInstance client = HazelcastCacheService.getInstance();
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
}
