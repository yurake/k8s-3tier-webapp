package webapp.tier.resource;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.resteasy.reactive.RestStreamElementType;

import io.smallrye.mutiny.Multi;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@Path("/quarkus/kafka")
public class KafkaResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private static MsgBean errormsg = new MsgBean(0, "Unexpected Error");

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "kafka.splitkey")
	String splitkey;

	@Inject
	@Channel("message")
	Multi<String> pubmsg;

	@Outgoing("converter")
	@POST
	@Path("/publish")
	public Multi<String> publish() {
		return Multi.createFrom().items(generateMessgae())
				.onFailure().recoverWithCompletion()
				.log();
	}

	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Path("/subscribe")
	@RestStreamElementType(MediaType.APPLICATION_JSON)
	public Multi<String> subscribe() {
		logger.log(Level.INFO, "Subscribe received.");
		return pubmsg.log();
	}

	private String generateMessgae() {
		MsgBean msgbean = errormsg;
		try {
			msgbean = new MsgBean(CreateId.createid(), message, "Generate");
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Create Id Error.", e);
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return MsgUtils.createBody(msgbean, splitkey);
	}
}
