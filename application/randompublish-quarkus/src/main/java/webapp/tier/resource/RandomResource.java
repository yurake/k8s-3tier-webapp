package webapp.tier.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

import webapp.tier.service.RandomService;

@ApplicationScoped
@Path("/random")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RandomResource {

	@Inject
	RandomService randomsvc;

	@GET
	@Counted(value = "performedChecks", description = "How many primality checks have been performed.")
	@Timed(value = "checksTimer", description = "A measure of how long it takes to perform the primality test.")
	public Response random() {
		try {
			return Response.ok().entity(randomsvc.deliverrandom(randomsvc.getNum(6)))
					.build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
