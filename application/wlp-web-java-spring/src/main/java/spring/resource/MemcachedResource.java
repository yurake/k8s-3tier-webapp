package spring.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import spring.web.cache.memcached.GetMemcached;
import spring.web.cache.memcached.SetMemcached;

@Path("/memcached")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemcachedResource {

	@POST
	@Path("/set")
	public Response set() {
		SetMemcached svc = new SetMemcached();
		try {
			return Response.ok().entity(svc.setMemcached()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response get() {
		GetMemcached svc = new GetMemcached();
		try {
			return Response.ok().entity(svc.getMemcached()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
