package webapp.tier;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;

import webapp.tier.cache.HazelcastCacheService;
import webapp.tier.mq.HazelcastMqService;
import webapp.tier.util.HazelcastInstanceConfigurator;

@RestController
@RequestMapping("/spring/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastController {

	HazelcastCacheService createHazelcastCacheService() {
		return new HazelcastCacheService();
	}

	HazelcastMqService createHazelcastMqService() {
		return new HazelcastMqService();
	}

	@PostMapping("/setcache")
	public Response putcache() {
		HazelcastCacheService svc = createHazelcastCacheService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.putMapHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/getcache")
	public Response getcache() {
		HazelcastCacheService svc = createHazelcastCacheService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.getMapHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/putqueue")
	public Response putqueue() {
		HazelcastMqService svc = createHazelcastMqService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.putQueueHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/getqueue")
	public Response getqueue() {
		HazelcastMqService svc = createHazelcastMqService();
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(svc.getQueueHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/publish")
	public Response publish() {
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
