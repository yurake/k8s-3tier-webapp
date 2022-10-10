package webapp.tier.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import webapp.tier.bean.LatestMessage;
import webapp.tier.service.KafkaService;

@ApplicationScoped
@Path("/quarkus/kafka")
public class KafkaResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	@Channel("in-memory-message")
	Publisher<String> inmemmsg;

	@Inject
	KafkaService svc;

	@Context
	Sse sse;

	@GET
	@Path("/subscribe")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType("text/plain")
	public Publisher<String> subscribe() {
		return inmemmsg;
	}

	@GET
	@Path("/get")
    @Retry(maxRetries = 3)
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		return Response.ok(LatestMessage.getLatestMsg()).build();
	}

	@POST
	@Path("/publish")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response publish() {
		try {
			return Response.ok().entity(svc.publishMsg()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Publish Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
