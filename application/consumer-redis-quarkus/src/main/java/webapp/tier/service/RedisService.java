package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.service.subscribe.RedisSubscriber;

@ApplicationScoped
public class RedisService {

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);

	public boolean ping() {
		Jedis jedis = new Jedis(servername, serverport);
		try {
			if (jedis.ping().equalsIgnoreCase("PONG")) {
				return true;
			}
		} catch (JedisConnectionException e) {
			LOG.log(Level.SEVERE, "Status Check Error.", e);
		} finally {
			jedis.close();
		}
		return false;
	}

	public void subscribeRedistoMysql() {

		Jedis jedis = new Jedis(servername, serverport);
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
