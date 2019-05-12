package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InsertDb {

	@RequestMapping("InsertDb")
	public String dbAccessInsertDb() {

		/**
		String result = null;
		System.out.println("InsertDB");

		InsertMessage insmsg = new InsertMessage();
		result = insmsg.doGet();
		**/

		return "insertdb";
	}
}
