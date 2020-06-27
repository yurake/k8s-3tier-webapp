package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@ApplicationScoped
public class ConnectHazelcast {
	private static String address = ConfigProvider.getConfig().getValue("hazelcast.address", String.class);
	private static String groupname = ConfigProvider.getConfig().getValue("hazelcast.group.name", String.class);

	public HazelcastInstance createNodeInstance() {
		Config config = new Config();
		RestApiConfig restApiConfig = new RestApiConfig().setEnabled(true).disableAllGroups()
				.enableGroups(RestEndpointGroup.DATA);
		config.getNetworkConfig().setRestApiConfig(restApiConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

	public static HazelcastInstance getInstance() throws RuntimeException {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getGroupConfig().setName(groupname);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
