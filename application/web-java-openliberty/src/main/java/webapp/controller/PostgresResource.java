package webapp.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webapp.tier.db.PostgresService;

@RestController
@RequestMapping("/postgres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostgresResource {


	PostgresService createPostgresService() {
		return new PostgresService();
	}

	@PostMapping("/insert")
	public Response insert() {
		PostgresService postgres = createPostgresService();
		try {
			return Response.ok().entity(postgres.insert()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GetMapping("/select")
	public Response select() {
		PostgresService postgres = createPostgresService();
		try {
			return Response.ok().entity(postgres.select()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@PostMapping("/delete")
	public Response delete() {
		PostgresService postgres = createPostgresService();
		try {
			return Response.ok(postgres.delete()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
