package webapp.tier.service.subscribe;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import redis.clients.jedis.JedisPubSub;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.RedisSocket;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisSubscriber extends JedisPubSub {

	private static final Logger LOG = Logger.getLogger(RedisSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);
	RedisSocket redissock = new RedisSocket();

	@Override
	public void onMessage(String channel, String message) {
		try {
			MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
			msgbean.setFullmsg("Received");
			LOG.log(Level.INFO, msgbean.getFullmsg());
			redissock.onMessage(MsgUtils.createBody(msgbean, splitkey));
			msgbean.setFullmsg("Broadcast");
			LOG.log(Level.INFO, msgbean.getFullmsg());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		}
	}

}
