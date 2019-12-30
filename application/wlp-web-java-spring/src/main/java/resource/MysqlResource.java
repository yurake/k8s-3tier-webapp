package resource;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import web.db.mysql.DeleteMysql;
import web.db.mysql.InsertMysql;
import web.db.mysql.SelectMysql;

@Path("/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlResource {

	@POST
	@Path("/insert")
	public Response insert() {
		InsertMysql insmysql = new InsertMysql();
		try {
			return Response.ok().entity(insmysql.insertMysql()).build();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/select")
	public Response select() {
		SelectMysql selmysql = new SelectMysql();
		try {
			return Response.ok().entity(selmysql.selectMsg()).build();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Path("/delete")
	public Response delete() {
		DeleteMysql delmysql = new DeleteMysql();
		try {
			return Response.ok(delmysql.deleteMsg()).build();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
