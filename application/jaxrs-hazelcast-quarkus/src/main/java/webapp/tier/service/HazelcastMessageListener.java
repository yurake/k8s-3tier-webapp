package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.HazelcastSocket;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastMessageListener implements MessageListener<Object> {

	private static final Logger LOG = Logger.getLogger(HazelcastMessageListener.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);
	private HazelcastSocket hazsock = new HazelcastSocket();

	@Override
	public void onMessage(Message<Object> message) {
		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(), splitkey);
		msgbean.setFullmsg("Received");
		LOG.log(Level.INFO, msgbean.getFullmsg());
		hazsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
		msgbean.setFullmsg("Broadcast");
		LOG.log(Level.INFO, msgbean.getFullmsg());
	}
}
