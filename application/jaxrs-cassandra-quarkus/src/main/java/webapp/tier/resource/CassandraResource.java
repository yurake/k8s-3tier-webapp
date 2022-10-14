package webapp.tier.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import webapp.tier.service.CassandraService;

@Path("/quarkus/cassandra")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CassandraResource {

	@Inject
	CassandraService svc;

	@POST
	@Path("/insert")
	@Counted(name = "performedChecks_insert", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_insert", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response insert() {
		try {
			return Response.ok().entity(svc.insertMsg()).build();
		} catch (Exception e) {
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
			return Response.ok().entity(svc.selectMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/delete")
	@Counted(name = "performedChecks_delete", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_delete", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response delete() {
		try {
			return Response.ok().entity(svc.deleteMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

}
