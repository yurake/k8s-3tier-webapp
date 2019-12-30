package webapp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapp.tier.mq.rabbitmq.GetRabbitmq;
import webapp.tier.mq.rabbitmq.PutRabbitmq;
import webapp.tier.mq.rabbitmq.PutRabbitmqConsumer;

@Path("/rabbitmq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RabbitmqResource {

	@POST
	@Path("/put")
	public Response set() {
		PutRabbitmq svc = new PutRabbitmq();
		try {
			return Response.ok().entity(svc.putMessageQueue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response get() {
		GetRabbitmq svc = new GetRabbitmq();
		try {
			return Response.ok().entity(svc.getMessageQueue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response publish() {
		PutRabbitmqConsumer svc = new PutRabbitmqConsumer();
		try {
			return Response.ok().entity(svc.putMessageQueueConsumer()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
