package webapp.tier;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
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
		logger.info(logMessageOut("insert", m.getId(), m.getMsg()));

		return messageRepository.save(m);
	}

	@GetMapping("/select")
	public @ResponseBody Iterable<Message> select() {
		List<Message> allmsg = new ArrayList<>();
		allmsg = messageRepository.findAll();

		if (allmsg.isEmpty()) {
			Message m = new Message();
			m.setId(0);
			m.setMsg("No Data");
			allmsg.add(m);
		}

		for(Message value : allmsg) {
			logger.info(logMessageOut("select", value.getId(), value.getMsg()));
		}

		return allmsg;
	}

	@PostMapping("/delete")
	public @ResponseBody String delete() {
		messageRepository.deleteAll();
		return "Deleted";
	}

	private String logMessageOut(String type, int id, String msg) {
		return type + ": id: " + id + ", msg: " + msg;
	}
}
