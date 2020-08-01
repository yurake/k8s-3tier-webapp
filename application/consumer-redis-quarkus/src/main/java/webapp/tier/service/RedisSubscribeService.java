package webapp.tier.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class RedisSubscribeService implements Runnable {

	private static final Logger LOG = Logger.getLogger(RedisSubscribeService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("The application is stopping...");
	}

	@Override
	public void run() {
		RedisService svc = new RedisService();
		svc.subscribeRedis();
	}

}
