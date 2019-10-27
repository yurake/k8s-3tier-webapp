package spring.web.cache.redis;

import redis.clients.jedis.Jedis;
import spring.web.util.CreateId;
import spring.web.util.GetConfig;

public class PublishRedis {
	private static String message = GetConfig.getResourceBundle("redis.publisher.message");
	private static String servername = GetConfig.getResourceBundle("redis.server.name");
	private static int serverport= Integer.parseInt(GetConfig.getResourceBundle("redis.server.port"));
	private static int setexpire = Integer.parseInt(GetConfig.getResourceBundle("redis.set.expire"));

	public String publishRedis() {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.publish(id, message);
			jedis.expire(id, setexpire);
			fullmsg = "Set id: " + id + ", msg: " + message;
			System.out.println(fullmsg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
