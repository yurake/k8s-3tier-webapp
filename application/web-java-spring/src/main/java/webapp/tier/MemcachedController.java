package webapp.tier;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.cache.MemcachedService;

@RestController
@RequestMapping("/spring/memcached")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemcachedController {

	MemcachedService createMemcachedService() {
		return new MemcachedService();
	}

	@PostMapping("/set")
	public Response set() {
		MemcachedService svc = createMemcachedService();
		try {
			return Response.ok().entity(svc.set()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/get")
	public Response get() {
		MemcachedService svc = createMemcachedService();
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
