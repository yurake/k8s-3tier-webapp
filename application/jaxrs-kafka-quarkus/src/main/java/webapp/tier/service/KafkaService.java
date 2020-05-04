package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import webapp.tier.bean.LatestMessage;

@ApplicationScoped
public class KafkaService {

	private static final Logger LOG = Logger.getLogger(KafkaService.class.getSimpleName());

	@Incoming("message")
	@Outgoing("in-memory-message")
	@Merge(Merge.Mode.MERGE)
	@Broadcast
	public String messegeToMemory(String msg) {
		LOG.log(Level.INFO, "Received: {0}", msg);
		LatestMessage.setLatestMsg(msg);
		return msg;
	}
}
