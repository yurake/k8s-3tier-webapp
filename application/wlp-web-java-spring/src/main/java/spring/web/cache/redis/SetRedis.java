package spring.web.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class SetRedis {
	Logger logger = LoggerFactory.getLogger(SetRedis.class);
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String servername = GetConfig.getResourceBundle("redis.server.name");
	private static int serverport= Integer.parseInt(GetConfig.getResourceBundle("redis.server.port"));
	private static int setexpire = Integer.parseInt(GetConfig.getResourceBundle("redis.set.expire"));

	public String setRedis() {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.set(id, message);
			jedis.expire(id, setexpire);
			fullmsg = "Set id: " + id + ", msg: " + message;
			logger.info(fullmsg);
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
