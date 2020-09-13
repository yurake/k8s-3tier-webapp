package webapp.tier.cache.redis;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class PublishRedis {
	Logger logger = LoggerFactory.getLogger(PublishRedis.class);
	private static String message = GetConfig.getResourceBundle("redis.publisher.message");
	private static String servername = GetConfig.getResourceBundle("redis.server.name");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.server.port"));
	private static int setexpire = Integer.parseInt(GetConfig.getResourceBundle("redis.set.expire"));
	private static String splitkey = GetConfig.getResourceBundle("redis.split.key");
	private static String channel = GetConfig.getResourceBundle("redis.channel.name");

	public String publishRedis() {
		String fullmsg = null;
		Jedis jedis = null;
		String id;
		try {
			id = String.valueOf(CreateId.createid());
			jedis = new Jedis(servername, serverport);
			StringBuilder buf = new StringBuilder();
			buf.append(id);
			buf.append(splitkey);
			buf.append(message);
			String body = buf.toString();

			jedis.publish(channel, body);
			jedis.expire(id, setexpire);
			fullmsg = "Set channel:" + channel + ", id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
