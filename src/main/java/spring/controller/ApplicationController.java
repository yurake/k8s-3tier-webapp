package spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.web.cache.memcached.GetMemcached;
import spring.web.cache.memcached.SetMemcached;
import spring.web.cache.redis.GetRedis;
import spring.web.cache.redis.SetRedis;
import spring.web.db.mysql.DeleteMysql;
import spring.web.db.mysql.InsertMysql;
import spring.web.db.mysql.SelectMysql;
import spring.web.mq.rabbitmq.GetRabbitmq;
import spring.web.mq.rabbitmq.PutRabbitmq;
import spring.web.mq.rabbitmq.PutRabbitmqConsumer;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("InsertMysql")
	public String insertDb(Model model) {

		System.out.println("InsertMysql");
		InsertMysql insmsg = new InsertMysql();
		String msg = insmsg.insertMysql();

		model.addAttribute("insertMysql", msg);

		return "insertmysql";
	}

	@RequestMapping("SelectMysql")
	public String selectMysql(Model model) {

		System.out.println("SelectMysql");
		SelectMysql insmsg = new SelectMysql();
		List<String> allMessage = insmsg.selectMsg();

		model.addAttribute("allMessageList", allMessage);

		return "selectmysql";
	}

	@RequestMapping("DeleteMysql")
	public String deleteMysql() {

		System.out.println("DeleteMysql");
		DeleteMysql insmsg = new DeleteMysql();
		insmsg.deleteMsg();

		return "deletemysql";
	}

	@RequestMapping("GetRabbitmq")
	public String getMq(Model model) {

		System.out.println("GetRabbitmq");
		GetRabbitmq getmq = new GetRabbitmq();
		String telegram = getmq.getMessageQueue();

		model.addAttribute("getRabbitmq", telegram);

		return "getrabbitmq";
	}

	@RequestMapping("PutRabbitmq")
	public String putMq(Model model) {

		System.out.println("PutRabbitmq");
		PutRabbitmq putmq = new PutRabbitmq();
		String telegram = putmq.putMessageQueue();

		model.addAttribute("putRabbitmq", telegram);

		return "putrabbitmq";
	}

	@RequestMapping("PutRabbitmqConsumer")
	public String putMqBatch(Model model) {

		System.out.println("PutRabbitmqConsumer");
		PutRabbitmqConsumer putmqb = new PutRabbitmqConsumer();
		String telegram = putmqb.putMessageQueueConsumer();

		model.addAttribute("putRabbitmqConsumer", telegram);

		return "putrabbitmqconsumer";
	}

	@RequestMapping("GetMemcached")
	public String getMemcached(Model model) {

		System.out.println("GetMemcached");
		GetMemcached getcache = new GetMemcached();
		String cache = getcache.getMemcached();

		model.addAttribute("getMemcached", cache);

		return "getmemcached";
	}

	@RequestMapping("SetMemcached")
	public String setMemcached(Model model) {

		System.out.println("SetMemcached");
		SetMemcached setcache = new SetMemcached();
		String cache = setcache.setMemcached();

		model.addAttribute("setMemcached", cache);

		return "setmemcached";
	}

	@RequestMapping("GetRedis")
	public String getRedis(Model model) {

		System.out.println("GetRedis");
		GetRedis getcache = new GetRedis();
		List<String> cache = getcache.getRedis();

		model.addAttribute("GetRedisList", cache);

		return "getredis";
	}

	@RequestMapping("SetRedis")
	public String setRedis(Model model) {

		System.out.println("SetRedis");
		SetRedis setcache = new SetRedis();
		String cache = setcache.setRedis();

		model.addAttribute("setRedis", cache);

		return "setredis";
	}
}
