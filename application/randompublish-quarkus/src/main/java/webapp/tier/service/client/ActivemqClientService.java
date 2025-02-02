package webapp.tier.service.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "activemq")
public interface ActivemqClientService {

	@POST
	@Path("/activemq/publish")
	@Produces("application/json")
	String publish();
}
