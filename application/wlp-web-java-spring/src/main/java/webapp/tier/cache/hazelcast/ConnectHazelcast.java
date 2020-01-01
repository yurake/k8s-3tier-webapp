package webapp.tier.cache.hazelcast;

import java.io.IOException;

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

import webapp.tier.util.GetConfig;

public class ConnectHazelcast {
	private static Logger logger = LoggerFactory.getLogger(ConnectHazelcast.class);
    private static final String DEFAULT_FALSE = "false";
	private static String clientxml = GetConfig.getResourceBundle("hazelcast.client.xml");
	private static String HAZELCAST_GROUP_NAME = GetConfig.getResourceBundle("hazelcast.group.name");
	private static String HAZELCAST_SERVICE_NAME = GetConfig.getResourceBundle("hazelcast.service.name");

	public HazelcastInstance createNodeInstance() {
		Config config = new Config();
		RestApiConfig restApiConfig = new RestApiConfig().setEnabled(true).disableAllGroups()
				.enableGroups(RestEndpointGroup.DATA);
		config.getNetworkConfig().setRestApiConfig(restApiConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

	public static HazelcastInstance getInstance() throws IOException {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getGroupConfig().setName(HAZELCAST_GROUP_NAME);

		boolean isk8s = System.getProperty("isk8s", DEFAULT_FALSE).equalsIgnoreCase("true");

		if (isk8s) {
			logger.info("Service on k8s");
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
			logger.info("Not service in k8s");
			clientConfig = new XmlClientConfigBuilder(clientxml).build();
			return HazelcastClient.newHazelcastClient(clientConfig);

//			clientConfig
//					.getNetworkConfig()
//					.setAddresses(Collections.singletonList("hazelcast:5701"));
		}
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
