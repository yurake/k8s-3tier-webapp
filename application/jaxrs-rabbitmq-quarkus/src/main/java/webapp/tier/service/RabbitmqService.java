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

import io.smallrye.reactive.messaging.annotations.Blocking;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.RabbitmqSocket;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class RabbitmqService {
	
	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Channel("converter") 
	Emitter<String> messageEmitter; 

	@ConfigProperty(name = "common.message")
	String message;
	
	@ConfigProperty(name = "rabbitmq.split.key")
	String splitkey;
	
	@Inject
	RabbitmqSocket rmqsock;

	public MsgBean publishMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		messageEmitter.send(MsgUtils.createBody(msgbean, splitkey));
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Incoming("message")
	@Blocking
	public void consume(String message) {
		MsgBean msgbean = MsgUtils.splitBody(message, splitkey);
		msgbean.setFullmsg("Received");
		logger.log(Level.INFO, msgbean.getFullmsg());
		
		rmqsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
		msgbean.setFullmsg("Broadcasted");
		logger.log(Level.INFO, msgbean.getFullmsg());
	}
}
