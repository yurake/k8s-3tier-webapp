package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

@ApplicationScoped
public final class HazelcastSubscribeService implements Runnable {
	
	@ConfigProperty(name = "hazelcast.topic.name")
	String topicname;

	private static String address = ConfigProvider.getConfig().getValue("hazelcast.address", String.class);

	private HazelcastSubscribeService() {
	}

	protected HazelcastDeliverSubscriber createHazelcastDeliverSubscriber() {
		return new HazelcastDeliverSubscriber();
	}
	
	public static HazelcastInstance getInstance() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		return HazelcastClient.newHazelcastClient(clientConfig);
	}
	
	void subscribeHazelcast(HazelcastInstance client, HazelcastDeliverSubscriber subscriber) {
		ITopic<Object> topic = client.getTopic(topicname);
		topic.addMessageListener(subscriber);
	}

	public void run() {
		subscribeHazelcast(HazelcastSubscribeService.getInstance(), createHazelcastDeliverSubscriber());
	}
}
