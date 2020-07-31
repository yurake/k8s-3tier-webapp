package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

@ApplicationScoped
public class HazelcastConnectionHelper {
	private static String address = ConfigProvider.getConfig().getValue("hazelcast.address", String.class);

	public static HazelcastInstance getInstance() throws RuntimeException {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
