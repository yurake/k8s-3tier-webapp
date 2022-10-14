package webapp.tier.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/random")
@RegisterRestClient(configKey = "random")
public interface HazelcastDeliverService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	String random();

}
