package webapp.tier.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import webapp.tier.service.MysqlService;

@Path("/quarkus/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Counted(name = "performedChecks", description = "How many primality checks have been performed.")
@Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
public class MysqlResource {

	MysqlService mysqlsvc = new MysqlService();

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
