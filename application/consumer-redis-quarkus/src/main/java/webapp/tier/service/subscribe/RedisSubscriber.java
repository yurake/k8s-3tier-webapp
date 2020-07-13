package webapp.tier.service.subscribe;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import redis.clients.jedis.JedisPubSub;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.DeliverService;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisSubscriber extends JedisPubSub {

	private static final Logger LOG = Logger.getLogger(RedisSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);

	@Override
	public void onMessage(String channel, String message) {
		MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
		msgbean.setFullmsg("Received");
		LOG.info(msgbean.getFullmsg());
		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();
		String response = deliversvc.random();
		LOG.log(Level.INFO, "Call Random Publish: {0}", response);
	}

}
