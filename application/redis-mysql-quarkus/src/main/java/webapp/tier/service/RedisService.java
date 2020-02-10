package webapp.tier.service;

import java.sql.Connection;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.ConfigProvider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Provider
public class RedisService {

	Connection con = null;

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);

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
					mysqlsvc.insertMsg(body);
				}
			}, channel);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

}
