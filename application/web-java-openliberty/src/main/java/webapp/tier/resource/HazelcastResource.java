package webapp.tier.resource;

import com.hazelcast.core.HazelcastInstance;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
