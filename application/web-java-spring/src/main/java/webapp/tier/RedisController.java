package webapp.tier;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.bean.MsgBean;
import webapp.tier.cache.RedisService;

@RestController
@RequestMapping("/spring/redis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RedisController {

	@Autowired
	RedisService svc;

	@PostMapping("/put")
	@ResponseBody
	public MsgBean set() {
		return svc.set();
	}

	@GetMapping("/get")
	@ResponseBody
	public Iterable<MsgBean> get() {
		return svc.get();
	}

	@PostMapping("/publish")
	@ResponseBody
	public MsgBean publish() {
		return svc.publish();
	}
}
