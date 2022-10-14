package webapp.tier.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Multi;

@Path("/random")
@RegisterRestClient(configKey = "random")
public interface RedisDeliverService {

	@GET
	@Produces("application/json")
	Multi<String> random();

}
