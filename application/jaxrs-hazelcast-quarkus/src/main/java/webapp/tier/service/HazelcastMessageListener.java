package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.HazelcastSocket;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastMessageListener implements MessageListener<Object> {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "hazelcast.split.key")
	String splitkey;

	@Inject
	HazelcastSocket hazsock;

	@Override
	public void onMessage(Message<Object> message) {
		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(), splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		hazsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
		msgbean.setFullmsg("Broadcast");
		logger.log(Level.INFO, msgbean.getFullmsg());
	}
}
