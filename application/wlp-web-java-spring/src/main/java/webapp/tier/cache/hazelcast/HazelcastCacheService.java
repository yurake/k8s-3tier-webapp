package webapp.tier.cache.hazelcast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.kubernetes.HazelcastKubernetesDiscoveryStrategyFactory;
import com.hazelcast.kubernetes.KubernetesProperties;
import com.hazelcast.spi.properties.GroupProperty;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class HazelcastCacheService {
	private static Logger logger = LoggerFactory.getLogger(HazelcastCacheService.class);
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String cachename = GetConfig.getResourceBundle("hazelcast.cache.name");
	private static String clientxml = GetConfig.getResourceBundle("hazelcast.client.xml");
	private static String HAZELCAST_SERVICE_NAME = GetConfig.getResourceBundle("hazelcast.service.name");
	private static HazelcastInstance instance = null;

	synchronized static public HazelcastInstance getInstance() throws Exception {
		if (instance == null) {
			instance = createClientk8sInstance();
		} else {
			instance = createClientk8sInstance();
		}
		return instance;
	}

	public static HazelcastInstance createNodeInstance() throws Exception {
		Config config = new Config();
		RestApiConfig restApiConfig = new RestApiConfig().setEnabled(true).disableAllGroups()
				.enableGroups(RestEndpointGroup.DATA);
		config.getNetworkConfig().setRestApiConfig(restApiConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

	public static HazelcastInstance createClientInstance() throws Exception {
		ClientConfig clientConfig = new XmlClientConfigBuilder(clientxml).build();
		return HazelcastClient.newHazelcastClient(clientConfig);
	}

	public static HazelcastInstance createClientk8sInstance() throws Exception {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getGroupConfig().setName("dev");

		boolean k8s = System.getenv("k8s").equalsIgnoreCase("true");
		logger.info("k8s property: " + System.getenv("k8s"));

		if (k8s) {
			logger.info("k8s is true");
			HazelcastKubernetesDiscoveryStrategyFactory hazelcastKubernetesDiscoveryStrategyFactory = new HazelcastKubernetesDiscoveryStrategyFactory();
			DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(
					hazelcastKubernetesDiscoveryStrategyFactory);
			discoveryStrategyConfig.addProperty(KubernetesProperties.SERVICE_DNS.key(), HAZELCAST_SERVICE_NAME);

			clientConfig.setProperty(GroupProperty.DISCOVERY_SPI_ENABLED.toString(), "true");
			clientConfig
					.getNetworkConfig()
					.getDiscoveryConfig()
					.addDiscoveryStrategyConfig(discoveryStrategyConfig);

		} else {
			logger.info("k8s is false");
			clientConfig
					.getNetworkConfig()
					.setAddresses(Collections.singletonList("hazelcast:5701"));
		}

		return HazelcastClient.newHazelcastClient(clientConfig);
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
