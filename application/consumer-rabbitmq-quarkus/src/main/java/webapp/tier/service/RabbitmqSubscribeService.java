package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.reactive.messaging.annotations.Blocking;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitmqSubscribeService {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	@RestClient
	RabbitmqDeliverService deliversvc;

	@ConfigProperty(name = "rabbitmq.split.key")
	String splitkey;

	@Incoming("message")
	@Blocking
	public void consume(String message) {
		MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		String response = deliversvc.random();
		logger.log(Level.INFO, "Called Random Publish: {0}", response);
	}

}
