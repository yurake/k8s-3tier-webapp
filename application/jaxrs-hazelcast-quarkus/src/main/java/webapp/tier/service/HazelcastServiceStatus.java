package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import com.hazelcast.core.HazelcastInstance;

@ApplicationScoped
public class HazelcastServiceStatus {

	private static final Logger LOG = Logger.getLogger(HazelcastServiceStatus.class.getSimpleName());

	public boolean isActive() {
		boolean status = false;
		HazelcastInstance client = null;
		try {
			client = createHazelcastInstance();
			status = client.getLifecycleService().isRunning();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return status;
	}

	protected HazelcastInstance createHazelcastInstance() {
		return HazelcastService.getInstance();
	}
}
