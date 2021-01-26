package webapp.tier.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
