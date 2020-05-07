package webapp.tier.resource;

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
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import webapp.tier.bean.LatestMessage;
import webapp.tier.service.KafkaService;

@ApplicationScoped
@Path("/quarkus/kafka")
public class KafkaResource {

	@Inject
	@Channel("in-memory-message")
	Publisher<String> inmemmsg;

	@Inject
	KafkaService svc;

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
	@Path("/publish")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response publish() {
		try {
			return Response.ok().entity(svc.publishMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
