package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.redis.datasource.value.ValueCommands;
import webapp.tier.bean.MsgBean;

@ApplicationScoped
class MockPublisher {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private final ValueCommands<String, MsgBean> commands;
	private final PubSubCommands<Notification> pub;

	@ConfigProperty(name = "redis.channel")
	String channel;

	public MockPublisher(RedisDataSource ds) {
		commands = ds.value(MsgBean.class);
		pub = ds.pubsub(Notification.class);
	}

	public MsgBean get(String key) {
		return commands.get(key);
	}

	public void set(String key, MsgBean msgbean) {
		commands.set(key, msgbean);
		pub.publish(channel, new Notification(key, msgbean));
		logger.log(Level.INFO, msgbean.getFullmsg());
	}
}
