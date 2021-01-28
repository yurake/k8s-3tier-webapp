package webapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.mq.RabbitmqService;

@RestController
@RequestMapping("/rabbitmq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RabbitmqResource {

	RabbitmqService createRabbitmqService() {
		return new RabbitmqService();
	}

	@PostMapping("/put")
	public Response put() {
		RabbitmqService svc = createRabbitmqService();
		try {
			return Response.ok().entity(svc.put()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/get")
	public Response get() {
		RabbitmqService svc = createRabbitmqService();
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/publish")
	public Response publish() {
		RabbitmqService svc = createRabbitmqService();
		try {
			return Response.ok().entity(svc.publish()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
