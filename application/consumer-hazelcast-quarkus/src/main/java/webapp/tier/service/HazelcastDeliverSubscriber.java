package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastDeliverSubscriber implements MessageListener<Object> {

	@RestClient
	HazelcastDeliverService deliversvc;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private static String splitkey = ConfigProvider.getConfig()
			.getValue("hazelcast.split.key", String.class);

	@Override
	public void onMessage(Message<Object> message) {
		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(),
				splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		String response = deliversvc.random();
		logger.log(Level.INFO, "Call Random Publish: {0}", response);
	}
}
