package webapp.tier.service.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "mongodb")
public interface MongodbClientService {

	@POST
	@Path("/mongodb/insert")
	@Produces("application/json")
	String insert();
}
