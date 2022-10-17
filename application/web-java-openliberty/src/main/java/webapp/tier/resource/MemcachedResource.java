package webapp.tier.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import webapp.tier.cache.MemcachedService;

@Path("/memcached")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemcachedResource {

	MemcachedService createMemcachedService() {
		return new MemcachedService();
	}

	@POST
	@Path("/set")
	public Response memset() {
		MemcachedService svc = createMemcachedService();
		try {
			return Response.ok().entity(svc.set()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response memget() {
		MemcachedService svc = createMemcachedService();
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
