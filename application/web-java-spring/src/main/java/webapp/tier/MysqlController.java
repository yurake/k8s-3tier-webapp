package webapp.tier;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.db.mysql.MysqlMessage;
import webapp.tier.db.mysql.MysqlMessageRepository;
import webapp.tier.util.CreateId;

@RestController
@RequestMapping("/spring/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${common.message}")
	private String message;

	@Autowired
	private MysqlMessageRepository messageRepository;

	@PostMapping("/insert")
	@ResponseBody
	public MysqlMessage insert() {
		MysqlMessage m = new MysqlMessage();
		m.setId(CreateId.createid());
		m.setMsg(message);
		logger.info(logMessageOut("insert", m.getId(), m.getMsg()));

		return messageRepository.save(m);
	}

	@GetMapping("/select")
	@ResponseBody
	public Iterable<MysqlMessage> select() {
		List<MysqlMessage> allmsg = new ArrayList<>();
		allmsg = messageRepository.findAll();

		if (allmsg.isEmpty()) {
			MysqlMessage m = new MysqlMessage();
			m.setId(0);
			m.setMsg("No Data");
			allmsg.add(m);
		}

		for(MysqlMessage value : allmsg) {
			logger.info(logMessageOut("select", value.getId(), value.getMsg()));
		}

		return allmsg;
	}

	@PostMapping("/delete")
	@ResponseBody
	public String delete() {
		messageRepository.deleteAll();
		String msg = "Deleted";
		logger.info(msg);
		return msg;
	}

	private String logMessageOut(String type, int id, String msg) {
		return type + ": id: " + id + ", msg: " + msg;
	}
}
