package webapp.tier.resource;

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

import webapp.tier.service.RabbitmqService;

@Path("/quarkus/rabbitmq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RabbitmqResource {

	@POST
	@Path("/put")
	@Counted(name = "performedChecks_put", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_put", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response set() {
		RabbitmqService svc = new RabbitmqService();
		try {
			return Response.ok().entity(svc.putMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/get")
	@Counted(name = "performedChecks_get", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_get", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response get() {
		RabbitmqService svc = new RabbitmqService();
		try {
			return Response.ok().entity(svc.getMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/publish")
	@Counted(name = "performedChecks_publish", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_publish", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response publish() {
		RabbitmqService svc = new RabbitmqService();
		try {
			return Response.ok().entity(svc.publishMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
