package webapp.tier.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Multi;

@Path("/random")
@RegisterRestClient(configKey = "random")
public interface RedisDeliverService {

	@GET
	@Produces("application/json")
	Multi<String> random();

}
