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

import webapp.tier.db.postgres.PostgresMessage;
import webapp.tier.db.postgres.PostgresMessageRepository;
import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

@RestController
@RequestMapping("/spring/postgres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostgresController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private static String message = GetConfig.getResourceBundle("common.message");

	@Autowired
	private PostgresMessageRepository messageRepository;

	@PostMapping("/insert")
	public @ResponseBody PostgresMessage insert() throws NoSuchAlgorithmException {
		int id = CreateId.createid();

		PostgresMessage m = new PostgresMessage();
		m.setId(id);
		m.setMsg(message);
		logger.info(logMessageOut("insert", m.getId(), m.getMsg()));

		return messageRepository.save(m);
	}

	@GetMapping("/select")
	public @ResponseBody Iterable<PostgresMessage> select() {
		List<PostgresMessage> allmsg = new ArrayList<>();
		allmsg = messageRepository.findAll();

		if (allmsg.isEmpty()) {
			PostgresMessage m = new PostgresMessage();
			m.setId(0);
			m.setMsg("No Data");
			allmsg.add(m);
		}

		for(PostgresMessage value : allmsg) {
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
