package web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import web.cache.GetCache;
import web.cache.SetCache;
import web.db.DeleteMessage;
import web.db.InsertMessage;
import web.db.SelectMessage;
import web.mq.GetMq;
import web.mq.PutMq;
import web.mq.PutMqBatch;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("InsertDB")
	public String insertDb(Model model) {

		System.out.println("InsertDB");
		InsertMessage insmsg = new InsertMessage();
		String msg = insmsg.insertMsg();

		model.addAttribute("insertMsg", msg);

		return "insertdb";
	}

	@RequestMapping("SelectDB")
	public String selectDb(Model model) {

		System.out.println("SelectDB");
		SelectMessage insmsg = new SelectMessage();
		List<String> allMessage = insmsg.selectMsg();

		model.addAttribute("allMessageList", allMessage);
		return "selectdb";
	}

	@RequestMapping("DeleteDB")
	public String deleteDb() {

		System.out.println("DeleteDB");
		DeleteMessage insmsg = new DeleteMessage();
		insmsg.deleteMsg();

		return "deletedb";
	}

	@RequestMapping("GetMQ")
	public String getMq(Model model) {

		System.out.println("GetMQ");
		GetMq getmq = new GetMq();
		String telegram = getmq.getMessageQueue();

		model.addAttribute("getMsgQueue", telegram);

		return "getmq";
	}

	@RequestMapping("PutMQ")
	public String putMq(Model model) {

		System.out.println("PutMQ");
		PutMq putmq = new PutMq();
		String telegram = putmq.putMessageQueue();

		model.addAttribute("putMsgQueue", telegram);

		return "putmq";
	}

	@RequestMapping("PutMQBatch")
	public String putMqBatch(Model model) {

		System.out.println("PutMQBatch");
		PutMqBatch putmqb = new PutMqBatch();
		String telegram = putmqb.putMessageQueueBatch();

		model.addAttribute("putMsgQueueBatch", telegram);

		return "putmqbatch";
	}

	@RequestMapping("GetCache")
	public String getCache(Model model) {

		System.out.println("GetCache");
		GetCache getcache = new GetCache();
		String cache = getcache.getCache();

		model.addAttribute("getCache", cache);

		return "getcache";
	}

	@RequestMapping("SetCache")
	public String setCache(Model model) {

		System.out.println("PutCache");
		SetCache setcache = new SetCache();
		String cache = setcache.setCache();

		model.addAttribute("setCache", cache);

		return "setcache";
	}
}
