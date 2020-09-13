package webapp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapp.tier.mq.RabbitmqService;

@Path("/rabbitmq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RabbitmqResource {

	@POST
	@Path("/put")
	public Response set() {
		RabbitmqService svc = new RabbitmqService();
		try {
			return Response.ok().entity(svc.put()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response get() {
		RabbitmqService svc = new RabbitmqService();
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response publish() {
		RabbitmqService svc = new RabbitmqService();
		try {
			return Response.ok().entity(svc.publish()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
