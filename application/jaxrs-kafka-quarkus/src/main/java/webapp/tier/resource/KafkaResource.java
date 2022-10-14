package webapp.tier.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

import io.smallrye.mutiny.Multi;

@Path("/quarkus/kafka")
public class KafkaResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	@Channel("message")
	Multi<String> pubmsg;

	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@Path("/subscribe")
	@RestStreamElementType(MediaType.APPLICATION_JSON)
	public Multi<String> subscribe() {
		logger.log(Level.INFO, "Subscribe received.");
		return pubmsg;
	}
}
