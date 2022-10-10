package webapp.tier.cache;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class RedisService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String servername = GetConfig.getResourceBundle("redis.server.name");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.server.port"));
	private static String splitkey = GetConfig.getResourceBundle("redis.split.key");
	private static String channel = GetConfig.getResourceBundle("redis.channel.name");
	private static int setexpire = Integer.parseInt(GetConfig.getResourceBundle("redis.set.expire"));

	public Jedis createJedis() {
		return new Jedis(servername, serverport);
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
				logger.error("Status Check Error. ", e);
			} finally {
				jedis.close();
			}
		}
		return status;
	}

	public String set() throws NoSuchAlgorithmException {
		String fullmsg = null;
		Jedis jedis = null;
		String id;
		try {
			id = String.valueOf(CreateId.createid());
			jedis = createJedis();
			jedis.set(id, message);
			jedis.expire(id, setexpire);
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} catch(Exception e) {
			logger.error("Set Error. ", e);
		} finally {
			jedis.close();
		}
		return fullmsg;
	}

	public List<String> get() {
		List<String> allmsg = new ArrayList<>();
		Jedis jedis = null;

		try {
			jedis = createJedis();
			Set<String> keys = jedis.keys("*");
			for (String key : keys) {
				String msg = jedis.get(key);
				String fullmsg = "Selected Msg: id: " + key + ", message: " + msg;
				logger.info(fullmsg);
				allmsg.add(fullmsg);
			}

			if (allmsg.isEmpty()) {
				allmsg.add("No Data");
			}

		} catch(Exception e) {
			logger.error("Get Error. ", e);
		} finally {
			jedis.close();
		}
		return allmsg;
	}

	public String publish() throws NoSuchAlgorithmException {
		String fullmsg = null;
		Jedis jedis = null;
		String id;
		try {
			jedis = createJedis();
			id = String.valueOf(CreateId.createid());
			StringBuilder buf = new StringBuilder();
			buf.append(id);
			buf.append(splitkey);
			buf.append(message);
			String body = buf.toString();

			jedis.publish(channel, body);
			jedis.expire(id, setexpire);
			fullmsg = "Publish channel:" + channel + ", id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} catch(Exception e) {
			logger.error("Publish Error. ", e);
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
