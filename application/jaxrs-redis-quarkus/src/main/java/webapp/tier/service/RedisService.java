package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
import webapp.tier.interfaces.Messaging;
import webapp.tier.service.subscribe.RedisSubscriber;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisService implements Messaging, Runnable {

	private static final Logger LOG = Logger.getLogger(RedisService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	static boolean isEnableReceived = true;

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

	public boolean ping() {
		Jedis jedis = new Jedis(servername, serverport);
		boolean status = false;
		try {
			if (jedis.ping().equalsIgnoreCase("PONG")) {
				status = true;
			}
		} catch (JedisConnectionException e) {
			LOG.log(Level.SEVERE, "Status Check Error.", e);
		} finally {
			jedis.close();
		}
		return status;
	}

	@Override
	public MsgBean putMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		Jedis jedis = new Jedis(servername, serverport);
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

	@Override
	public MsgBean getMsg() throws RuntimeException {
		List<MsgBean> msglist = getMsgList();
		return msglist.get(msglist.size() - 1);
	}

	public List<MsgBean> getMsgList() throws RuntimeException {
		List<MsgBean> msglist = new ArrayList<>();
		Jedis jedis = new Jedis(servername, serverport);
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

	@Override
	public MsgBean publishMsg() throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);
		Jedis jedis = new Jedis(servername, serverport);
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
		Jedis jedis = new Jedis(servername, serverport);
		RedisSubscriber redissubsc = new RedisSubscriber();

		try {
			jedis.subscribe(redissubsc, channel);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		} finally {
			jedis.close();
		}
	}

	public static void startReceived() {
		isEnableReceived = true;
	}

	public static void stopReceived() {
		isEnableReceived = false;
	}
}
