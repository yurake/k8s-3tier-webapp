package webapp.tier.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

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

import webapp.tier.service.RedisService;

@Path("/quarkus/redis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RedisResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	RedisService svc;

	@POST
	@Path("/put")
	@Counted(name = "performedChecks_put", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_ptt", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response put() {
		try {
			return Response.ok().entity(svc.putMsg(svc.createJedis())).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Put Error.", e);
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
			return Response.ok().entity(svc.getMsgList(svc.createJedis())).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Get Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/publish")
	@Counted(name = "performedChecks_publish", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_publish", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response publish() {
		try {
			return Response.ok().entity(svc.publishMsg(svc.createJedis())).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Publish Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
