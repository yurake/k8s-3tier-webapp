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

import webapp.tier.service.HazelcastCacheService;
import webapp.tier.service.HazelcastMqService;
import webapp.tier.service.HazelcastService;

@Path("/quarkus/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	HazelcastCacheService cachesvc;

	@Inject
	HazelcastMqService mqsvc;

	@POST
	@Path("/setcache")
	@Counted(name = "performedChecks_setcache", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_setcache", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response putcache() {
		try {
			return Response.ok().entity(cachesvc.setMsg(HazelcastService.getInstance()))
					.build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Set Cache Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getcache")
	@Retry(maxRetries = 3)
	@Counted(name = "performedChecks_getcache", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_getcache", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response getcache() {
		try {
			return Response.ok()
					.entity(cachesvc.getMsgList(HazelcastService.getInstance()))
					.build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Get Cache Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/putqueue")
	@Counted(name = "performedChecks_putqueue", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_putqueue", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response putqueue() {
		try {
			return Response.ok().entity(mqsvc.putMsg(HazelcastService.getInstance()))
					.build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Put Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getqueue")
	@Retry(maxRetries = 3)
	@Counted(name = "performedChecks_getqueue", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_getqueue", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response getqueue() {
		try {
			return Response.ok().entity(mqsvc.getMsg(HazelcastService.getInstance()))
					.build();
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
			return Response.ok().entity(mqsvc.publishMsg(HazelcastService.getInstance()))
					.build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Publish Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
