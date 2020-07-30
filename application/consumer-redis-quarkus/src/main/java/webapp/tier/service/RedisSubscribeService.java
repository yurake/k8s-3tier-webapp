package webapp.tier.service;

import java.util.Objects;
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

	private static final Logger LOG = Logger.getLogger(RedisSubscribeService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("The application is stopping...");
	}

	public boolean ping() {
		Jedis jedis = createJedis();
		boolean status = false;

		if (Objects.nonNull(jedis)) {
			try {
				if (jedis.ping().equalsIgnoreCase("PONG")) {
					status = true;
				}
			} catch (JedisConnectionException e) {
				LOG.log(Level.SEVERE, "Status Check Error.", e);
			} finally {
				jedis.close();
			}
		}
		return status;
	}

	public Jedis createJedis() {
		return new Jedis(servername, serverport);
	}

	@Override
	public void run() {
		Jedis jedis = createJedis();
		RedisSubscriber redissubsc = new RedisSubscriber();

		try {
			jedis.subscribe(redissubsc, channel);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		} finally {
			jedis.close();
		}
	}

}
