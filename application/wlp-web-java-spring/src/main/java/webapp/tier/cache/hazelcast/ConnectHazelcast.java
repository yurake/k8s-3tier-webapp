package webapp.tier.cache.hazelcast;

import java.io.IOException;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;

import webapp.tier.util.GetConfig;

public class ConnectHazelcast {
	private static String clientxml = GetConfig.getResourceBundle("hazelcast.client.xml");

	public static HazelcastInstance getInstance() throws IOException {
		ClientConfig clientConfig = new XmlClientConfigBuilder(clientxml).build();
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
}
