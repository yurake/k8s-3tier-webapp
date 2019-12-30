package webapp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapp.tier.cache.redis.GetRedis;
import webapp.tier.cache.redis.PublishRedis;
import webapp.tier.cache.redis.SetRedis;

@Path("/redis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RedisResource {

	@POST
	@Path("/set")
	public Response set() {
		SetRedis svc = new SetRedis();
		try {
			return Response.ok().entity(svc.setRedis()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response get() {
		GetRedis svc = new GetRedis();
		try {
			return Response.ok().entity(svc.getRedis()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response publish() {
		PublishRedis svc = new PublishRedis();
		try {
			return Response.ok().entity(svc.publishRedis()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
