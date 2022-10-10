package webapp.tier.resource;

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


	PostgresService createPostgresService() {
		return new PostgresService();
	}

	@POST
	@Path("/insert")
	public Response posinsert() {
		PostgresService postgres = createPostgresService();
		try {
			return Response.ok().entity(postgres.insert()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/select")
	public Response posselect() {
		PostgresService postgres = createPostgresService();
		try {
			return Response.ok().entity(postgres.select()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/delete")
	public Response posdelete() {
		PostgresService postgres = createPostgresService();
		try {
			return Response.ok(postgres.delete()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
