package webapp.tier.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import webapp.tier.service.RandomService;

@Path("/random")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Counted(name = "performedChecks", description = "How many primality checks have been performed.")
@Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
public class RandomResource {

	@Inject
	RandomService randomsvc;

	@GET
	public String random() {
		try {
//			return Response.ok().entity(randomsvc.deliverrandom()).build();
			return randomsvc.deliverrandom();
		} catch (Exception e) {
			e.printStackTrace();
//			return Response.status(500).build();
			return e.getMessage();
		}
	}
}
