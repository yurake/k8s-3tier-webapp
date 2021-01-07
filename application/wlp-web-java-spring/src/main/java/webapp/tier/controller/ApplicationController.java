package webapp.tier.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.tier.cache.MemcachedService;
import webapp.tier.cache.RedisService;
import webapp.tier.db.MysqlService;
import webapp.tier.mq.RabbitmqService;

@Controller
public class ApplicationController {

	@Inject
	MysqlService mysqlsvc;

	@Inject
	RabbitmqService rabbitmqsvc;

	@Inject
	MemcachedService memcachedsvc;

	@Inject
	RedisService redissvc;

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("InsertMysql")
	public String insertDb(Model model) throws Exception {
		logger.info("InsertMysql");
		String msg = mysqlsvc.insert();
		model.addAttribute("insertMysql", msg);
		return "insertmysql";
	}

	@RequestMapping("SelectMysql")
	public String selectMysql(Model model) throws SQLException, NamingException {
		logger.info("SelectMysql");
		List<String> allMessage = mysqlsvc.select();
		model.addAttribute("allMessageList", allMessage);
		return "selectmysql";
	}

	@RequestMapping("DeleteMysql")
	public String deleteMysql() throws SQLException, NamingException {
		logger.info("DeleteMysql");
		mysqlsvc.delete();
		return "deletemysql";
	}

	@RequestMapping("GetRabbitmq")
	public String getMq(Model model) throws Exception {
		logger.info("GetRabbitmq");
		String telegram = rabbitmqsvc.get();
		model.addAttribute("getRabbitmq", telegram);
		return "getrabbitmq";
	}

	@RequestMapping("PutRabbitmq")
	public String putMq(Model model) throws IOException, TimeoutException, NoSuchAlgorithmException {
		logger.info("PutRabbitmq");
		String telegram = rabbitmqsvc.put();
		model.addAttribute("putRabbitmq", telegram);
		return "putrabbitmq";
	}

	@RequestMapping("PutRabbitmqConsumer")
	public String putMqBatch(Model model) throws IOException, TimeoutException, NoSuchAlgorithmException {
		logger.info("PutRabbitmqConsumer");
		String telegram = rabbitmqsvc.publish();
		model.addAttribute("putRabbitmqConsumer", telegram);
		return "putrabbitmqconsumer";
	}

	@RequestMapping("GetMemcached")
	public String getMemcached(Model model) {
		logger.info("GetMemcached");
		String cache = memcachedsvc.get();
		model.addAttribute("getMemcached", cache);
		return "getmemcached";
	}

	@RequestMapping("SetMemcached")
	public String setMemcached(Model model) throws Exception {
		logger.info("SetMemcached");
		String cache = memcachedsvc.set();
		model.addAttribute("setMemcached", cache);
		return "setmemcached";
	}

	@RequestMapping("GetRedis")
	public String getRedis(Model model) {
		logger.info("GetRedis");
		List<String> cache = redissvc.get();
		model.addAttribute("getRedisList", cache);
		return "getredis";
	}

	@RequestMapping("SetRedis")
	public String setRedis(Model model) throws NoSuchAlgorithmException {
		logger.info("SetRedis");
		String cache = redissvc.set();
		model.addAttribute("setRedis", cache);
		return "setredis";
	}

	@RequestMapping("PublishRedis")
	public String publishRedis(Model model) throws NoSuchAlgorithmException {
		logger.info("PublishRedis");
		String cache = redissvc.publish();
		model.addAttribute("publishRedis", cache);
		return "publishredis";
	}
}
