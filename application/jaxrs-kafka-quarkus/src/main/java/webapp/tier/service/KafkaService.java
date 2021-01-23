package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import webapp.tier.bean.LatestMessage;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class KafkaService {

	private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "kafka.splitkey")
	String splitkey;

	@Inject
	@Channel("message")
	Emitter<String> emitmsg;

	@Incoming("message")
	@Outgoing("in-memory-message")
	@Merge(Merge.Mode.MERGE)
	@Broadcast
	public String messegeToMemory(String msg) {
		LOG.log(Level.INFO, "Received: {0}", msg);
		LatestMessage.setLatestMsg(msg);
		return msg;
	}

	public MsgBean publishMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		LOG.info(msgbean.getFullmsg());
		emitmsg.send(MsgUtils.createBody(msgbean, splitkey));
		return msgbean;
	}
}
