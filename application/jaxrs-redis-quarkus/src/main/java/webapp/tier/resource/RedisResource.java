package webapp.tier.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import webapp.tier.service.RedisService;

@Path("/quarkus/redis")
public class RedisResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	RedisService svc;

	@POST
	@Path("/put")
	public Response put() {
		try {
			return Response.ok().entity(svc.putMsg()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Put Error.", e);
			return Response.status(500).entity("Put Error.").build();
		}
	}

	@GET
	@Path("/get")
	public Response get() {
		try {
			return Response.ok().entity(svc.getMsgList()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Get Error.", e);
			return Response.status(500).entity("Get Error.").build();
		}
	}

	@DELETE
	@Path("/delete")
	public Response delete() {
		try {
			return Response.ok().entity(svc.delete()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Delete Error.", e);
			return Response.status(500).entity("Delete Error.").build();
		}

	}

	@POST
	@Path("/publish")
	public Response publish() {
		try {
			return Response.ok().entity(svc.publish()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Publish Error.", e);
			return Response.status(500).entity("Publish Error.").build();
		}
	}
}
