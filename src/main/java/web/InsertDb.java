package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InsertDb {

	@RequestMapping("InsertDB")
	public String dbAccessInsertDb() {

		System.out.println("InsertDB");

		InsertMessage insmsg = new InsertMessage();
		insmsg.insertMsg();

		return "insertdb";
	}
}
