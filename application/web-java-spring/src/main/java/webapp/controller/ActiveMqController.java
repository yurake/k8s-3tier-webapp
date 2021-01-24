package webapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.mq.ActiveMqService;

@RestController
@RequestMapping("/activemq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActiveMqController {

	ActiveMqService createActiveMqService() {
		return new ActiveMqService();
	}

	@PostMapping("/put")
	public Response putcache() {
		ActiveMqService svc = createActiveMqService();
		try {
			return Response.ok().entity(svc.putActiveMq()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/get")
	public Response getcache() {
		ActiveMqService svc = createActiveMqService();
		try {
			return Response.ok().entity(svc.getActiveMq()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/publish")
	public Response publish() {
		ActiveMqService svc = createActiveMqService();
		try {
			return Response.ok().entity(svc.publishActiveMq()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
