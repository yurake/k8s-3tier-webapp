package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import webapp.tier.util.GetConfig;

@ApplicationScoped
public class RedisService {

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());
	private static String servername = GetConfig.getResourceBundle("redis.server");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.port"));
	private static String channel = GetConfig.getResourceBundle("redis.channel");
	private static String splitkey = GetConfig.getResourceBundle("redis.splitkey");

	public void subscribeRedistoMysql() {

		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.subscribe(new JedisPubSub() {
				@Override
				public void onMessage(String channel, String message) {
					String fullmsg = null;
					String[] body = message.split(splitkey, 0);
					fullmsg = "Received channel:" + channel + ", id: " + body[0] + ", msg: " + body[1];
					System.out.println(fullmsg);

					MysqlService mysqlsvc = new MysqlService();
					mysqlsvc.insertMysql(body);
				}
			}, channel);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Error: ", e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
