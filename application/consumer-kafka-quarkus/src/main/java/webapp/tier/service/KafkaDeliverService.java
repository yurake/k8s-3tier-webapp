package webapp.tier.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/random")
@RegisterRestClient(configKey = "random")
public interface KafkaDeliverService {

	@GET
	@Produces("application/json")
	@Retry(maxRetries = 3)
	String random();

}
