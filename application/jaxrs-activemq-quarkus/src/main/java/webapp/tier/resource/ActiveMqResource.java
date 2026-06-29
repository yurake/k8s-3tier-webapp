package webapp.tier.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Retry;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

import webapp.tier.service.ActiveMqService;

@Path("/quarkus/activemq")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActiveMqResource {

	@Inject
	ActiveMqService svc;

	@POST
	@Path("/put")
	@Counted(value = "performedChecks_put", description = "How many primality checks have been performed.")
	@Timed(value = "checksTimer_put", description = "A measure of how long it takes to perform the primality test.")
	public Response putcache() {
		try {
			return Response.ok().entity(svc.putMsg().getFullmsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/get")
	@Retry(maxRetries = 3)
	@Counted(value = "performedChecks_get", description = "How many primality checks have been performed.")
	@Timed(value = "checksTimer_get", description = "A measure of how long it takes to perform the primality test.")
	public Response getcache() {
		try {
			return Response.ok().entity(svc.getMsg().getFullmsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/publish")
	@Counted(value = "performedChecks_publish", description = "How many primality checks have been performed.")
	@Timed(value = "checksTimer_publish", description = "A measure of how long it takes to perform the primality test.")
	public Response publish() {
		try {
			return Response.ok().entity(svc.publishMsg().getFullmsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
