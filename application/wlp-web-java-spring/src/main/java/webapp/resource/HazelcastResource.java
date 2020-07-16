package webapp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.cache.hazelcast.ConnectHazelcast;
import webapp.tier.cache.hazelcast.HazelcastCacheService;
import webapp.tier.mq.hazelcast.HazelcastMqService;

@Path("/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastResource {

	@POST
	@Path("/putcache")
	public Response putcache() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			HazelcastInstance client = ConnectHazelcast.getInstance();
			return Response.ok().entity(svc.putMapHazelcast(client)).build();
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
			HazelcastInstance client = ConnectHazelcast.getInstance();
			return Response.ok().entity(svc.getMapHazelcast(client)).build();
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
			HazelcastInstance client = ConnectHazelcast.getInstance();
			return Response.ok().entity(svc.putQueueHazelcast(client)).build();
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
			HazelcastInstance client = ConnectHazelcast.getInstance();
			return Response.ok().entity(svc.getQueueHazelcast(client)).build();
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
			HazelcastInstance client = ConnectHazelcast.getInstance();
			return Response.ok().entity(svc.publishHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
