package webapp.tier.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.cache.HazelcastCacheService;
import webapp.tier.mq.HazelcastMqService;
import webapp.tier.util.HazelcastInstanceConfigurator;

@Path("/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastResource {

	HazelcastCacheService createHazelcastCacheService() {
		return new HazelcastCacheService();
	}

	HazelcastMqService createHazelcastMqService() {
		return new HazelcastMqService();
	}

	@POST
	@Path("/putcache")
	public Response hazputcache() {
		HazelcastCacheService svc = createHazelcastCacheService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.putMapHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/getcache")
	public Response hazgetcache() {
		HazelcastCacheService svc = createHazelcastCacheService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.getMapHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/putqueue")
	public Response hazputqueue() {
		HazelcastMqService svc = createHazelcastMqService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.putQueueHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/getqueue")
	public Response hazgetqueue() {
		HazelcastMqService svc = createHazelcastMqService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.getQueueHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/publish")
	public Response hazpublish() {
		HazelcastMqService svc = createHazelcastMqService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.publishHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
