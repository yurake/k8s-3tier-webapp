package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import redis.clients.jedis.Jedis;
import webapp.tier.util.GetConfig;

@ApplicationScoped
public class RedisSubscribeServiceToMysql implements Runnable {

	private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());
	private static String servername = GetConfig.getResourceBundle("redis.server");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.port"));
	private static String channel = GetConfig.getResourceBundle("redis.channel");

	public Jedis createJedis() {
		return new Jedis(servername, serverport);
	}

	protected RedisSubscriberToMysql createRedisDeliverSubscriber() {
		return new RedisSubscriberToMysql();
	}

	protected void subscribeRedis(RedisSubscriberToMysql redissubsc) {
		Jedis jedis = createJedis();
		try {
			jedis.subscribe(redissubsc, channel);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		} finally {
			jedis.close();
		}
	}

	@Override
	public void run() {
		subscribeRedis(createRedisDeliverSubscriber());
	}

}
