package webapp.tier.resource;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import webapp.tier.service.PostgresService;
import webapp.tier.bean.MsgBean;

@Path("/quarkus/postgres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostgresResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	PostgresService posgressvc;

	@POST
	@Path("/insert")
	@Counted(name = "performedChecks_insert", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_insert", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response insert() {
		try {
			return Response.ok().entity(posgressvc.insertMsg().getFullmsg()).build();
		} catch (NoSuchAlgorithmException | SQLException e) {
			logger.log(Level.WARNING, "Insert Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/select")
	@Retry(maxRetries = 3)
	@Counted(name = "performedChecks_select", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_select", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response select() {
		try {
			String result = posgressvc.selectMsg().stream()
					.map(MsgBean::getFullmsg)
					.collect(Collectors.joining(","));
			return Response.ok().entity(result).build();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Select Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/delete")
	@Counted(name = "performedChecks_delete", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_delete", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response delete() {
		try {
			return Response.ok().entity(posgressvc.deleteMsg()).build();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Delete Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
