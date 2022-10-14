package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public final class HazelcastSubscribeService implements MessageListener<String> {

	@RestClient
	HazelcastDeliverService deliversvc;

	private static final HazelcastSubscribeService INSTANCE = new HazelcastSubscribeService();
	private static final Logger logger = Logger
			.getLogger(HazelcastSubscribeService.class.getSimpleName());

	private static String topicname = ConfigProvider.getConfig()
			.getValue("hazelcast.topic.name", String.class);
	private static String address = ConfigProvider.getConfig()
			.getValue("hazelcast.address", String.class);
	private static String splitkey = ConfigProvider.getConfig()
			.getValue("hazelcast.split.key", String.class);
	private static HazelcastInstance hazelcastInstance;

	//Hide Constractor
	private HazelcastSubscribeService() {
	}

	static {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
	}

	void onStart(@Observes StartupEvent ev) {

		ITopic<String> topic = hazelcastInstance.getTopic(topicname);
		topic.addMessageListener(this);
		logger.log(Level.INFO, "Subscribing...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		logger.log(Level.INFO, "Unsubscibed.");
		hazelcastInstance.shutdown();
	}

	public static HazelcastSubscribeService getInstance() {
		return INSTANCE;
	}
	
	public static HazelcastInstance getHazelcastInstance() {
		return hazelcastInstance;
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
