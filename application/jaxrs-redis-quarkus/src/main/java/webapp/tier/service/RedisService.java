package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisService implements Consumer<RedisNotification> {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "redis.split.key")
	String splitkey;

	private static String channel = ConfigProvider.getConfig().getValue("redis.channel",
			String.class);

	@ConfigProperty(name = "common.message")
	String message;

	private final KeyCommands<String> keys;
	private final ValueCommands<String, String> msgs;
	private final PubSubCommands<RedisNotification> pub;
	private final PubSubCommands.RedisSubscriber subscriber;

	public RedisService(RedisDataSource ds) {
		keys = ds.key();
		msgs = ds.value(String.class);
		pub = ds.pubsub(RedisNotification.class);
		subscriber = pub.subscribe(channel, this);
	}

	public MsgBean putMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		logger.log(Level.INFO, msgbean.getFullmsg());
		msgs.set(MsgUtils.intToString(msgbean.getId()), msgbean.getMessage());
		return msgbean;
	}

	public List<MsgBean> getMsgList() {
		logger.log(Level.INFO, "Get message list.");
		List<MsgBean> msglist = new ArrayList<>();
		List<String> keyList = keys.keys("*");
		for (String key : keyList) {
			MsgBean msgbean = new MsgBean(MsgUtils.stringToInt(key), msgs.get(key),
					"Get");
			logger.log(Level.INFO, msgbean.getFullmsg());
			msglist.add(msgbean);
		}
		if (msglist.isEmpty()) {
			msglist.add(new MsgBean(0, "No Data."));
		}
		return msglist;
	}

	public List<MsgBean> delete() {
		logger.log(Level.INFO, "Delete message all.");
		List<MsgBean> msglist = new ArrayList<>();
		List<String> keyList = keys.keys("*");
		for (String key : keyList) {
			MsgBean msgbean = new MsgBean(MsgUtils.stringToInt(key), msgs.get(key),
					"Delete");
			keys.del(key);
			logger.log(Level.INFO, msgbean.getFullmsg());
			msglist.add(msgbean);
		}
		return msglist;
	}

	public MsgBean publish() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		logger.log(Level.INFO, msgbean.getFullmsg());
		pub.publish(channel,
				new RedisNotification(MsgUtils.intToString(msgbean.getId()), msgbean));
		return msgbean;
	}

	@Override
	public void accept(RedisNotification notification) {
		MsgBean msgbean = notification.msgbean;
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
	}

	@PreDestroy
	public void terminate() {
		logger.log(Level.INFO, "Unsubscibed.");
		subscriber.unsubscribe();
	}
}
