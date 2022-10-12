package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import io.quarkus.runtime.Startup;

@ApplicationScoped
@Startup
public final class HazelcastSubscribeService {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static HazelcastInstance hazelcastInstance;

	private static String topicname = ConfigProvider.getConfig()
			.getValue("hazelcast.topic.name", String.class);
	private static String address = ConfigProvider.getConfig()
			.getValue("hazelcast.address", String.class);

	public HazelcastSubscribeService() {
		if (hazelcastInstance == null) {
			ClientConfig clientConfig = new ClientConfig();
			clientConfig.getNetworkConfig().addAddress(address);
			clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
					.setClusterConnectTimeoutMillis(5000)
					.setMaxBackoffMillis(10000);
			hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
			ITopic<Object> topic = hazelcastInstance.getTopic(topicname);
			topic.addMessageListener(new HazelcastDeliverSubscriber());
			logger.log(Level.INFO, "Subscribing...");
		}
	}

	public HazelcastSubscribeService(HazelcastInstance hazelcastInstance) {
		ITopic<Object> topic = hazelcastInstance.getTopic(topicname);
		topic.addMessageListener(new HazelcastDeliverSubscriber());
		logger.log(Level.INFO, "Subscribing...");
	}

	@PreDestroy
	public void terminate() {
		logger.log(Level.INFO, "Unsubscibed.");
		hazelcastInstance.shutdown();
	}

	public boolean isActive() {
		boolean status = false;
		try {
			status = hazelcastInstance.getLifecycleService().isRunning();
			return status;
		} catch (IllegalStateException e) {
			logger.log(Level.SEVERE, "Connect Error.", e);
		}
		return status;
	}

}
