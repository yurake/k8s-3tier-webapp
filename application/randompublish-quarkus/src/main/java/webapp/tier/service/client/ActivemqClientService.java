package webapp.tier.service.client;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "activemq")
public interface ActivemqClientService {

	@POST
	@Path("/activemq/publish")
	@Produces("application/json")
	String publish();
}