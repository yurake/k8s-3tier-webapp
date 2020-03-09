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

import webapp.tier.service.HazelcastCacheService;
import webapp.tier.service.HazelcastMqService;

@Path("/quarkus/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastResource {

	@POST
	@Path("/putcache")
	@Counted(name = "performedChecks_putcache", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_putcache", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response putcache() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			return Response.ok().entity(svc.setMsg()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getcache")
	@Counted(name = "performedChecks_getcache", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_getcache", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response getcache() {
		HazelcastCacheService svc = new HazelcastCacheService();
		try {
			return Response.ok().entity(svc.getMsgList()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/putqueue")
	@Counted(name = "performedChecks_putqueue", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_putqueue", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response putqueue() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			return Response.ok().entity(svc.putMsg()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getqueue")
	@Counted(name = "performedChecks_getqueue", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_getqueue", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response getqueue() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			return Response.ok().entity(svc.getMsg()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/publish")
	@Counted(name = "performedChecks_publish", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_publish", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response publish() {
		HazelcastMqService svc = new HazelcastMqService();
		try {
			return Response.ok().entity(svc.publishMsg()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
