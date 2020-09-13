package webapp.resource;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapp.tier.db.PostgresService;

@Path("/postgres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostgresResource {


	@POST
	@Path("/insert")
	public Response insert() {
		PostgresService postgres = new PostgresService();
		try {
			return Response.ok().entity(postgres.insert()).build();
		} catch (SQLException | NamingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/select")
	public Response select() {
		PostgresService postgres = new PostgresService();
		try {
			return Response.ok().entity(postgres.select()).build();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/delete")
	public Response delete() {
		PostgresService postgres = new PostgresService();
		try {
			return Response.ok(postgres.delete()).build();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
