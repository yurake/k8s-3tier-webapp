package webapp.tier.service.client;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "mongodb")
public interface MongodbClientService {

	@POST
	@Path("/mongodb/insert")
	@Produces("application/json")
	String insert();
}
