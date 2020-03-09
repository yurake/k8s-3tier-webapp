package webapp.tier.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.ConfigProvider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.interfaces.Messaging;
import webapp.tier.util.CreateId;

@Provider
public class RedisService implements Messaging {

	Connection con = null;

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);
	private static int setexpire = ConfigProvider.getConfig().getValue("redis.set.expire", Integer.class);

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

	@Override
	public String getMsg() throws Exception {
		List<String> msglist = getMsgList();
		return msglist.get(msglist.size() - 1);
	}

	public List<String> getMsgList() throws Exception {
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

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			jedis.close();
		}
		return allmsg;
	}

	@Override
	public String putMsg() throws Exception {
		String fullmsg = null;
		String id = String.valueOf(CreateId.createid());
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.set(id, message);
			jedis.expire(id, setexpire);
			fullmsg = "Set id: " + id + ", msg: " + message;
			LOG.info(fullmsg);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		} finally {
			jedis.close();
		}
		return fullmsg;
	}

	@Override
	public String publishMsg() throws Exception {
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

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		} finally {
			jedis.close();
		}
		return fullmsg;
	}
}
