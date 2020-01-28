package webapp.tier.service;

import java.sql.Connection;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.util.GetConfig;

@Provider
public class RedisService {

	Connection con = null;

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private static String servername = GetConfig.getResourceBundle("redis.server");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.port"));
	private static String channel = GetConfig.getResourceBundle("redis.channel");
	private static String splitkey = GetConfig.getResourceBundle("redis.splitkey");

	public boolean ping() {
		Jedis jedis = new Jedis(servername, serverport);
		try {
			if (jedis.ping().equalsIgnoreCase("PONG")) {
				return true;
			}
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return false;
	}

	public void subscribeRedistoMysql() {

		MysqlService mysqlsvc = new MysqlService();
		Jedis jedis = new Jedis(servername, serverport);
		try {
			jedis.subscribe(new JedisPubSub() {
				@Override
				public void onMessage(String channel, String message) {
					String fullmsg = null;
					String[] body = message.split(splitkey, 0);
					fullmsg = "Received channel:" + channel + ", id: " + body[0]+ ", msg: " + body[1];
					LOG.info(fullmsg);
					mysqlsvc.insertMysql(body);
				}
			}, channel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}
}
