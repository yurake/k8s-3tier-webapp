package org.acme.resource;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.service.MysqlService;

@Path("/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlResource {

	@Inject
	MysqlService mysqlsvc;

	@POST
	@Path("/insert")
	public Response insert() {
		try {
			return Response.ok().entity(mysqlsvc.insertMysql()).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/select")
	public Response select() {
		try {
			return Response.ok().entity(mysqlsvc.selectMysql()).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
