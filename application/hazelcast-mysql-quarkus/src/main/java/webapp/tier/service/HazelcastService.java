package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import webapp.tier.service.subscribe.HazelcastSubscriber;

@ApplicationScoped
public class HazelcastService {

	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topic.name", String.class);

	public boolean isActive() {
		HazelcastInstance client = null;
		boolean status = false;
		try {
			client = ConnectHazelcast.getInstance();
			status = client.getLifecycleService().isRunning();
		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullmsg;
	}
}
