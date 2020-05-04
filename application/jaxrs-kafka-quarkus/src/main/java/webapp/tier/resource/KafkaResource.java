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

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.reactivestreams.Publisher;

import webapp.tier.bean.LatestMessage;

@ApplicationScoped
@Path("/quarkus/kafka")
public class KafkaResource {

	private static final Logger LOG = Logger.getLogger(KafkaResource.class.getSimpleName());

	@Inject
	@Channel("in-memory-message")
	Publisher<String> inmemmsg;

	@Inject
	@Channel("message")
	Emitter<String> emitmsg;

	@Context
	private Sse sse;

	@GET
	@Path("/subscribe")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@SseElementType("text/plain")
	public Publisher<String> subscribe() {
		return inmemmsg;
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		return Response.ok(LatestMessage.getLatestMsg()).build();
	}

	@POST
	@Path("/broadcast/{msg}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response addPrice(@PathParam String msg) {
		LOG.log(Level.INFO, "Broadcasted: {0}", msg);
		return Response.ok(emitmsg.send(msg)).build();
	}
}
