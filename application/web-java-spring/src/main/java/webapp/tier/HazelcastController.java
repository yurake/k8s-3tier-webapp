package webapp.tier;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	HazelcastCacheService cachesvc;

	@Autowired
	HazelcastMqService mqsvc;


	@PostMapping("/setcache")
	public Response putcache() {
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(cachesvc.putMapHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/getcache")
	public Response getcache() {
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(cachesvc.getMapHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/putqueue")
	public Response putqueue() {
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(mqsvc.putQueueHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/getqueue")
	public Response getqueue() {
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(mqsvc.getQueueHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/publish")
	public Response publish() {
		try {
			HazelcastInstance client = HazelcastInstanceConfigurator.getInstance();
			return Response.ok().entity(mqsvc.publishHazelcast(client)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
