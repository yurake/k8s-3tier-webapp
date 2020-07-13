package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.smallrye.reactive.messaging.annotations.Merge;

@ApplicationScoped
public class ChannelEmitter {

	private static final Logger LOG = Logger.getLogger(ChannelEmitter.class.getSimpleName());

	static String respmsg;

	public static String getRespmsg() {
		return respmsg;
	}

	@Incoming("message")
	@Merge(Merge.Mode.MERGE)
	public String consume(String msg) {
		LOG.log(Level.INFO, "TEST Received: {0}", msg);
		respmsg = msg;
		return msg;
	}

}
