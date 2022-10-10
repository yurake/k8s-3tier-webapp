package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastDeliverSubscriber implements MessageListener<Object> {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "hazelcast.split.key")
	String splitkey;

	@Override
	public void onMessage(Message<Object> message) {
		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(), splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
