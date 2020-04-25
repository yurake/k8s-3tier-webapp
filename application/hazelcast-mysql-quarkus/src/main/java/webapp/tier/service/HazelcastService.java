package webapp.tier.service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.service.subscribe.HazelcastSubscriber;

@ApplicationScoped
public class HazelcastService implements Runnable {

	private static final Logger LOG = Logger.getLogger(HazelcastService.class.getSimpleName());
	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topic.name", String.class);
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("The application is stopping...");
	}

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

	@Override
	public void run() {
		HazelcastSubscriber hazsubsc = new HazelcastSubscriber();

		try {
			HazelcastInstance client = ConnectHazelcast.getInstance();
			ITopic<String> topic = client.getTopic(topicname);
			topic.addMessageListener(hazsubsc);
		} catch (IOException | IllegalStateException e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		}
	}
}
