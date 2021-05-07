package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import redis.clients.jedis.JedisPubSub;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.RedisSocket;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisSubscriber extends JedisPubSub {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	RedisSocket redissock;

	@ConfigProperty(name = "redis.splitkey")
	String splitkey;

	@Override
	public void onMessage(String channel, String message) {
		MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
		msgbean.setFullmsg("Subscribe");
		logger.log(Level.INFO, msgbean.getFullmsg());
		redissock.onMessage(MsgUtils.createBody(msgbean, splitkey));
	}
}
