package webapp.tier.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
