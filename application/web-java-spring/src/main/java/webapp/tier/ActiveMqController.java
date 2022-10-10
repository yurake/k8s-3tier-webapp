package webapp.tier;

import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.bean.MsgBean;
import webapp.tier.mq.ActiveMqService;

@RestController
@RequestMapping("/spring/activemq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActiveMqController {

	@Autowired
	ActiveMqService svc;

	@PostMapping("/put")
	@ResponseBody
	public MsgBean put() {
		return svc.put();
	}

	@GetMapping("/get")
	public Response get() {
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (JMSException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/publish")
	public MsgBean publish() {
		return svc.publish();
	}
}
