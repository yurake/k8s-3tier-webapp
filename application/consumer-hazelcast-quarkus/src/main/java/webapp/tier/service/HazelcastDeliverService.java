package webapp.tier.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/random")
@RegisterRestClient(configKey = "random")
public interface HazelcastDeliverService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	String random();

}
