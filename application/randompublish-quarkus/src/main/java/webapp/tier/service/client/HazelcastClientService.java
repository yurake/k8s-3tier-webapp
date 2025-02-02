package webapp.tier.service.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "hazelcast")
public interface HazelcastClientService {

	@POST
	@Path("/hazelcast/publish")
	@Produces("application/json")
	String publish();
}
