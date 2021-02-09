package webapp.tier.util;

import java.io.IOException;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastInstanceConfigurator {

	private static String address = GetConfig.getResourceBundle("hazelcast.address");

	private HazelcastInstanceConfigurator() {
	}

	public static HazelcastInstance getInstance() throws IOException {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(1000)
				.setMaxBackoffMillis(2000);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
