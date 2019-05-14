package web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import web.db.DeleteMessage;
import web.db.InsertMessage;
import web.db.SelectMessage;

@Controller
public class ApplicationController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("InsertDB")
	public String insertDb() {

		System.out.println("InsertDB");
		InsertMessage insmsg = new InsertMessage();
		insmsg.insertMsg();

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
}
