package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@Singleton
public final class HazelcastSubscribeService implements MessageListener<String> {

	@RestClient
	HazelcastDeliverService deliversvc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static HazelcastInstance hazelcastInstance;
	private static ClientConfig clientConfig = new ClientConfig();

	private static String topicname = ConfigProvider.getConfig()
			.getValue("hazelcast.topic.name", String.class);
	private static String address = ConfigProvider.getConfig()
			.getValue("hazelcast.address", String.class);
	private static String splitkey = ConfigProvider.getConfig()
			.getValue("hazelcast.split.key", String.class);

	void onStart(@Observes StartupEvent ev) {
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		HazelcastSubscribeService.hazelcastInstance = HazelcastClient
				.newHazelcastClient(clientConfig);
		ITopic<String> topic = hazelcastInstance.getTopic(topicname);
		topic.addMessageListener(this);
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

	@Override
	public void onMessage(Message<String> message) {
		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(),
				splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		String response = deliversvc.random();
		logger.log(Level.INFO, "Called Random Publish: {0}", response);
	}

}
