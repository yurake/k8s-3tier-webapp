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
import javax.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.client.WebappClientService;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisService implements Runnable {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String servername = ConfigProvider.getConfig().getValue("redis.server", String.class);
	private static int serverport = ConfigProvider.getConfig().getValue("redis.port.num", Integer.class);
	private static String channel = ConfigProvider.getConfig().getValue("redis.channel", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);
	private static int setexpire = ConfigProvider.getConfig().getValue("redis.set.expire", Integer.class);

	@Inject
	WebappClientService webappcltsvc;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.log(Level.INFO, "Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.log(Level.INFO, "Subscribe is stopping...");
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
				logger.log(Level.SEVERE, "Status Check Error.", e);
				throw e;
			} finally {
				jedis.close();
			}
		}
		return status;
	}

	@SuppressWarnings("deprecation")
	public MsgBean putMsg(Jedis jedis) throws RuntimeException, NoSuchAlgorithmException {
		int grpcid = webappcltsvc.getId();
		String grpcMsg = webappcltsvc.getMsg();

		MsgBean msgbean = new MsgBean(grpcid, grpcMsg, "Put");

		try {
			String id = MsgUtils.intToString(msgbean.getId());
			jedis.set(id, msgbean.getMessage());
			jedis.expire(id, setexpire);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Put Error. ", e);
			throw e;
		} finally {
			jedis.close();
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(Jedis jedis) throws RuntimeException {
		List<MsgBean> msglist = getMsgList(jedis);
		return msglist.get(msglist.size() - 1);
	}

	public List<MsgBean> getMsgList(Jedis jedis) throws RuntimeException {
		List<MsgBean> msglist = new ArrayList<>();

		try {
			Set<String> keys = jedis.keys("*");
			for (String key : keys) {
				MsgBean msgbean = new MsgBean(MsgUtils.stringToInt(key), jedis.get(key), "Get");
				logger.log(Level.INFO, msgbean.getFullmsg());
				msglist.add(msgbean);
			}

			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data."));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Get Error. ", e);
			throw e;
		} finally {
			jedis.close();
		}
		return msglist;
	}

	@SuppressWarnings("deprecation")
	public MsgBean publishMsg(Jedis jedis) throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try {
			jedis.publish(channel, body);
			jedis.expire(MsgUtils.intToString(msgbean.getId()), setexpire);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Publish Error. ", e);
			throw e;
		} finally {
			jedis.close();
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public void run() {
		subscribeRedis(createRedisSubscriber());
	}

	protected void subscribeRedis(RedisSubscriber redissubsc) {
		Jedis jedis = createJedis();

		try {
			jedis.subscribe(redissubsc, channel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Subscribe Error.", e);
			throw e;
		} finally {
			jedis.close();
		}
	}

	protected void flushAll(Jedis jedis) {
		jedis.flushAll();
	}
}
