package webapp.tier.util;

import java.io.IOException;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastInstanceConfigurator {

	private static String clientxml = GetConfig.getResourceBundle("hazelcast.client.xml");

	private HazelcastInstanceConfigurator() {
	}

	public static HazelcastInstance getInstance() throws IOException {
		ClientConfig clientConfig = new XmlClientConfigBuilder(clientxml).build();
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
