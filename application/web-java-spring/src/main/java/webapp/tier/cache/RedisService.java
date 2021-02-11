package webapp.tier.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.GetConfig;

@Service
public class RedisService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String servername = GetConfig.getResourceBundle("redis.server.name");
	private static int serverport = Integer.parseInt(GetConfig.getResourceBundle("redis.server.port"));
	private static String splitkey = GetConfig.getResourceBundle("redis.split.key");
	private static String channel = GetConfig.getResourceBundle("redis.channel.name");
	private static int setexpire = Integer.parseInt(GetConfig.getResourceBundle("redis.set.expire"));

	public Jedis createJedis() {
		return new Jedis(servername, serverport);
	}

	public boolean ping() {
		Jedis jedis = createJedis();
		boolean status = false;

		if (Objects.nonNull(jedis)) {
			try {
				if (jedis.ping().equalsIgnoreCase("PONG")) {
					status = true;
				}
			} catch (JedisConnectionException e) {
				logger.error("Status Check Error.", e);
			} finally {
				jedis.close();
			}
		}
		return status;
	}

	public MsgBean set() {
		MsgBean msgbean = new MsgBean(message);
		Jedis jedis = null;
		try {
			jedis = createJedis();
			jedis.set(msgbean.idtoString(), msgbean.getMessage());
			jedis.expire(msgbean.idtoString(), setexpire);
			logger.info(msgbean.logMessageOut("set"));
		} finally {
			jedis.close();
		}
		return msgbean;
	}

	public Iterable<MsgBean> get() {
		List<MsgBean> allmsg = new ArrayList<>();
		Jedis jedis = null;
		try {
			jedis = createJedis();
			Set<String> keys = jedis.keys("*");
			for (String key : keys) {
				String msg = jedis.get(key);
				MsgBean msgbean = new MsgBean(key, msg);
				logger.info(msgbean.logMessageOut("get"));
				allmsg.add(msgbean);
			}

			if (allmsg.isEmpty()) {
				allmsg.add(new MsgBean(0, "No Data"));
			}
		} finally {
			jedis.close();
		}
		return allmsg;
	}

	public MsgBean publish() {
		MsgBean msgbean = new MsgBean(message);
		String body = msgbean.createBody(splitkey);
		Jedis jedis = null;
		try {
			jedis = createJedis();
			jedis.publish(channel, body);
			jedis.expire(msgbean.idtoString(), setexpire);
			logger.info(msgbean.logMessageOut("publish"));
		} finally {
			jedis.close();
		}
		return msgbean;
	}
}
