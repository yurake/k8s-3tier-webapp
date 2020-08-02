package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisService implements Runnable {

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);
	private static int setexpire = ConfigProvider.getConfig().getValue("redis.set.expire", Integer.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("Subscribe is stopping...");
	}

	public Jedis createJedis() {
		return new Jedis(servername, serverport);
	}

	protected RedisSubscriber createRedisSubscriber() {
		return new RedisSubscriber();
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
				LOG.log(Level.SEVERE, "Status Check Error.", e);
			} finally {
				jedis.close();
			}
		}
		return status;
	}

	public MsgBean putMsg(Jedis jedis) throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String errormsg = "Put Error.";

		try {
			String id = MsgUtils.intToString(msgbean.getId());
			jedis.set(id, msgbean.getMessage());
			jedis.expire(id, setexpire);

		} catch (JedisConnectionException e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new RuntimeException(errormsg);
		} finally {
			jedis.close();
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(Jedis jedis) throws RuntimeException {
		List<MsgBean> msglist = getMsgList(jedis);
		return msglist.get(msglist.size() - 1);
	}

	public List<MsgBean> getMsgList(Jedis jedis) throws RuntimeException {
		List<MsgBean> msglist = new ArrayList<>();
		String errormsg = "Get Error.";

		try {
			Set<String> keys = jedis.keys("*");
			for (String key : keys) {
				MsgBean msgbean = new MsgBean(MsgUtils.stringToInt(key), jedis.get(key), "Get");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean);
			}

			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data."));
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new RuntimeException(errormsg);
		} finally {
			jedis.close();
		}
		return msglist;
	}

	public MsgBean publishMsg(Jedis jedis) throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);
		String errormsg = "Publish Error.";

		try {
			jedis.publish(channel, body);
			jedis.expire(MsgUtils.intToString(msgbean.getId()), setexpire);

		} catch (Exception e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new RuntimeException(errormsg);
		} finally {
			jedis.close();
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public void run() {
		subscribeRedis(createRedisSubscriber());
	}

	void subscribeRedis(RedisSubscriber redissubsc) {
		Jedis jedis = createJedis();

		try {
			jedis.subscribe(redissubsc, channel);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		} finally {
			jedis.close();
		}
	}

	protected void flushAll(Jedis jedis) {
		jedis.flushAll();
	}
}
