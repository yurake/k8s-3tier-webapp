package webapp.tier.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

@ApplicationScoped
public class RedisSubscribeService implements Runnable {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.log(Level.INFO, "Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.log(Level.INFO, "Subscribe is stopping...");
	}

	public Jedis createJedis() {
		return new Jedis(servername, serverport);
	}

	public boolean ping() {
		Jedis jedis = createJedis();
		boolean status = false;

		try {
			if (jedis.ping().equalsIgnoreCase("PONG")) status = true;
		} catch (JedisConnectionException e) {
			logger.log(Level.SEVERE, "Status Check Error.", e);
		} finally {
			jedis.close();
		}
		return status;
	}

	@Override
	public void run() {
		new RedisDeliverSubscriber();
	}

}
