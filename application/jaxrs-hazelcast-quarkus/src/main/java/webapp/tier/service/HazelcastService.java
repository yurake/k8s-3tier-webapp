package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

import com.hazelcast.core.HazelcastInstance;

@ApplicationScoped
public class HazelcastService {

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
}
