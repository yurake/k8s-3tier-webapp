package webapp.tier.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import webapp.tier.mq.RabbitmqService;

@Path("/rabbitmq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RabbitmqResource {

	RabbitmqService createRabbitmqService() {
		return new RabbitmqService();
	}

	@POST
	@Path("/put")
	public Response rabput() {
		RabbitmqService svc = createRabbitmqService();
		try {
			return Response.ok().entity(svc.put()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response rabget() {
		RabbitmqService svc = createRabbitmqService();
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response rabpublish() {
		RabbitmqService svc = createRabbitmqService();
		try {
			return Response.ok().entity(svc.publish()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
