package webapp.tier.service;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

@ApplicationScoped
public final class HazelcastService {

	private static String address = ConfigProvider.getConfig().getValue(
			"hazelcast.address",
			String.class);

	private HazelcastService() {
	}

	public static HazelcastInstance getInstance() throws RuntimeException {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
