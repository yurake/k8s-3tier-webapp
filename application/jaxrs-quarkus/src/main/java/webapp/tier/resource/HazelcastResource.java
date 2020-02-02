package webapp.tier.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapp.tier.service.HazelcastCacheService;
import webapp.tier.service.HazelcastMqService;

@Path("/quarkus/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastResource {

	@POST
	@Path("/putcache")
	public Response putcache() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			return Response.ok().entity(svc.putMapHazelcast()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/getcache")
	public Response getcache() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			return Response.ok().entity(svc.getMapHazelcast()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/putqueue")
	public Response putqueue() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			return Response.ok().entity(svc.putQueueHazelcast()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/getqueue")
	public Response getqueue() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			return Response.ok().entity(svc.getQueueHazelcast()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response publish() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			return Response.ok().entity(svc.publishHazelcast()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
