package webapp.tier.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import webapp.tier.db.MysqlService;

@Path("/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlResource {

	MysqlService createMysqlService() {
		return new MysqlService();
	}

	@POST
	@Path("/insert")
	public Response mysinsert() {
		MysqlService insmysql = createMysqlService();
		try {
			return Response.ok().entity(insmysql.insert()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/select")
	public Response mysselect() {
		MysqlService selmysql = createMysqlService();
		try {
			return Response.ok().entity(selmysql.select()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/delete")
	public Response mysdelete() {
		MysqlService delmysql = createMysqlService();
		try {
			return Response.ok(delmysql.delete()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
