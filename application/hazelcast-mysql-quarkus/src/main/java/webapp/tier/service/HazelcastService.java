package webapp.tier.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import webapp.tier.service.subscribe.HazelcastSubscriber;

@ApplicationScoped
public class HazelcastService {

	private static final Logger LOG = Logger.getLogger(HazelcastService.class.getSimpleName());
	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topic.name", String.class);

	public boolean isActive() {
		HazelcastInstance client = null;
		boolean status = false;
		try {
			client = ConnectHazelcast.getInstance();
			status = client.getLifecycleService().isRunning();
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return status;
	}

	public String subscribeHazelcast() {
		String fullmsg = "Error";
		HazelcastSubscriber hazsubsc = new HazelcastSubscriber();

		try {
			HazelcastInstance client = ConnectHazelcast.getInstance();
			ITopic<String> topic = client.getTopic(topicname);
			topic.addMessageListener(hazsubsc);
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		}
		return fullmsg;
	}
}
