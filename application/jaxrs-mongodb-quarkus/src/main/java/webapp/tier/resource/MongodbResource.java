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

import org.bson.Document;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import com.mongodb.client.MongoCollection;

import webapp.tier.service.MongodbService;

@Path("/quarkus/mongodb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MongodbResource {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	MongodbService mongosvc;

	@POST
	@Path("/insert")
	@Counted(name = "performedChecks_insert", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_insert", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response insert() {
		try {
			MongoCollection<Document> collection = mongosvc.getCollection();
			return Response.ok().entity(mongosvc.insertMsg(collection)).build();
		} catch (Exception e) {
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
			MongoCollection<Document> collection = mongosvc.getCollection();
			return Response.ok().entity(mongosvc.selectMsg(collection)).build();
		} catch (Exception e) {
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
			MongoCollection<Document> collection = mongosvc.getCollection();
			return Response.ok().entity(mongosvc.deleteMsg(collection)).build();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Delete Error.", e);
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
