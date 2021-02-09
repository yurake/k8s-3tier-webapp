package webapp.tier;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.db.Message;
import webapp.tier.db.MessageRepository;
import webapp.tier.db.MysqlService;
import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

@RestController
@RequestMapping("/spring/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlController {

	private static String message = GetConfig.getResourceBundle("common.message");

	@Autowired
	private MessageRepository messageRepository;

	MysqlService createMysqlService() {
		return new MysqlService();
	}

	@PostMapping("/insert")
	public @ResponseBody Message insert() throws NoSuchAlgorithmException {
		int id = CreateId.createid();

		Message m = new Message();
		m.setId(id);
		m.setMsg(message);

		return messageRepository.save(m);
	}

	@GetMapping("/select")
	public @ResponseBody Iterable<Message> select() {
		return messageRepository.findAll();
	}

	@PostMapping("/delete")
	public @ResponseBody String delete() {
		messageRepository.deleteAll();
		return "Deleted";
	}
}
