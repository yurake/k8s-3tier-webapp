package webapp.tier.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

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

import webapp.tier.service.MemcachedService;

@Path("/quarkus/memcached")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemcachedResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	MemcachedService svc;

	@POST
	@Path("/set")
	@Counted(name = "performedChecks_set", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_set", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response set() {
		try {
			return Response.ok().entity(svc.setMsg(svc.createMemCachedClient()).getFullmsg()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Set Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/get")
	@Retry(maxRetries = 3)
	@Counted(name = "performedChecks_get", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_get", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response get() {
		try {
			return Response.ok().entity(svc.getMsg(svc.createMemCachedClient()).getFullmsg()).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Get Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
