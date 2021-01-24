package webapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.db.MysqlService;

@RestController
@RequestMapping("/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlController {

	MysqlService createMysqlService() {
		return new MysqlService();
	}

	@PostMapping("/insert")
	public Response insert() {
		MysqlService insmysql = createMysqlService();
		try {
			return Response.ok().entity(insmysql.insert()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/select")
	public Response select() {
		MysqlService selmysql = createMysqlService();
		try {
			return Response.ok().entity(selmysql.select()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/delete")
	public Response delete() {
		MysqlService delmysql = createMysqlService();
		try {
			return Response.ok(delmysql.delete()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
