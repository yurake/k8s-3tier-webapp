package webapp.tier.service;

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
import webapp.tier.util.MsgBeanUtils;

@Provider
public class RedisService implements Messaging {

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);
	private static int setexpire = ConfigProvider.getConfig().getValue("redis.set.expire", Integer.class);

	public boolean ping() {
		Jedis jedis = new Jedis(servername, serverport);
		boolean status = false;
		try {
			if (jedis.ping().equalsIgnoreCase("PONG")) {
				status = true;
			}
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return status;
	}

	@Override
	public String putMsg() throws Exception {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.set(msgbean.getIdString(), msgbean.getMessage());
			jedis.expire(msgbean.getIdString(), setexpire);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Put Error.");
		} finally {
			jedis.close();
		}
		msgbean.setFullmsgWithType(msgbean, "Put");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public String getMsg() throws Exception {
		List<String> msglist = getMsgList();
		return msglist.get(msglist.size() - 1);
	}

	public List<String> getMsgList() throws Exception {
		List<String> msglist = new ArrayList<>();
		Jedis jedis = new Jedis(servername, serverport);

		try {
			Set<String> keys = jedis.keys("*");
			for (String key : keys) {
				MsgBeanUtils msgbean = new MsgBeanUtils(Integer.parseInt(key), jedis.get(key));
				msgbean.setFullmsgWithType(msgbean, "Get");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean.getFullmsg());
			}

			if (msglist.isEmpty()) {
				msglist.add("No Data");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Get Error.");
		} finally {
			jedis.close();
		}
		return msglist;
	}

	@Override
	public String publishMsg() throws Exception {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String body = msgbean.createBody(msgbean, splitkey);
		Jedis jedis = new Jedis(servername, serverport);

		try {
			jedis.publish(channel, body);
			jedis.expire(msgbean.getIdString(), setexpire);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Publish Error.");
		} finally {
			jedis.close();
		}
		msgbean.setFullmsgWithType(msgbean, "Publish");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}
}
