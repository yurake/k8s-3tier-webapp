package webapp.tier.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import com.mongodb.client.MongoCollection;

import webapp.tier.service.MongodbService;

@Path("/quarkus/mongodb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MongodbResource {

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
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/select")
	@Counted(name = "performedChecks_select", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_select", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response select() {
		try {
			MongoCollection<Document> collection = mongosvc.getCollection();
			return Response.ok().entity(mongosvc.selectMsg(collection)).build();
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
			MongoCollection<Document> collection = mongosvc.getCollection();
			return Response.ok().entity(mongosvc.deleteMsg(collection)).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
