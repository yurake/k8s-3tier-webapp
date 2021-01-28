package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RedisDeliverSubscriber extends RedisSubscriber {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);

	@Override
	public void onMessage(String channel, String message) {
		MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
	}

}
