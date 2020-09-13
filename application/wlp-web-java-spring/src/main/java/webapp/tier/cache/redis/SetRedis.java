package webapp.tier.cache.redis;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class SetRedis {
	Logger logger = LoggerFactory.getLogger(SetRedis.class);
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String servername = GetConfig.getResourceBundle("redis.server.name");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.server.port"));
	private static int setexpire = Integer.parseInt(GetConfig.getResourceBundle("redis.set.expire"));

	public String setRedis() {
		String fullmsg = null;
		Jedis jedis = null;
		String id;
		try {
			id = String.valueOf(CreateId.createid());
			jedis = new Jedis(servername, serverport);
			jedis.set(id, message);
			jedis.expire(id, setexpire);
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
