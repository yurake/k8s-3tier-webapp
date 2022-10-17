package webapp.tier.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import webapp.tier.mq.ActiveMqService;

@Path("/activemq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActiveMqResource {

	ActiveMqService createActiveMqService() {
		return new ActiveMqService();
	}

	@POST
	@Path("/put")
	public Response actputcache() {
		ActiveMqService svc = createActiveMqService();
		try {
			return Response.ok().entity(svc.putActiveMq()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response actgetcache() {
		ActiveMqService svc = createActiveMqService();
		try {
			return Response.ok().entity(svc.getActiveMq()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response actpublish() {
		ActiveMqService svc = createActiveMqService();
		try {
			return Response.ok().entity(svc.publishActiveMq()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
