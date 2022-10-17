package webapp.tier.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
