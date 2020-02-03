package webapp.tier.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.constant.EnumService;
import webapp.tier.util.CreateId;

@Provider
public class RedisService {

	Connection con = null;

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private static String message = EnumService.common_message.getString();
	private static String servername = EnumService.redis_server.getString();
	private static int serverport = Integer.parseInt(EnumService.redis_port.getString());
	private static String channel = EnumService.redis_channel.getString();
	private static String splitkey = EnumService.redis_splitkey.getString();
	private static int setexpire = Integer.parseInt(EnumService.redis_set_expire.getString());

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

	public String publishRedis() {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
		Jedis jedis = new Jedis(servername, serverport);

		try {
		    StringBuilder buf = new StringBuilder();
		    buf.append(id);
		    buf.append(splitkey);
		    buf.append(message);
		    String body = buf.toString();

			jedis.publish(channel, body);
			jedis.expire(id, setexpire);
			fullmsg = "Set channel:" + channel + ", id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		} finally {
			jedis.close();
		}
		return fullmsg;
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

	public List<String> getRedis() {
		List<String> allmsg = new ArrayList<>();
		Jedis jedis = new Jedis(servername, serverport);

		try {
			Set<String> keys = jedis.keys("*");
			for (String key : keys) {
				String msg = jedis.get(key);
				String fullmsg = "Selected Msg: id: " + key + ", message: " + msg;
				LOG.info(fullmsg);
				allmsg.add(fullmsg);
			}

			if (allmsg.isEmpty()) {
				allmsg.add("No Data");
			}

		} finally {
			jedis.close();
		}
		return allmsg;
	}

	public String setRedis() {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.set(id, message);
			jedis.expire(id, setexpire);
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
