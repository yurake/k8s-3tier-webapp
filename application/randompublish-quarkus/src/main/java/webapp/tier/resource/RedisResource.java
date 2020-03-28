package webapp.tier.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "redis")
public interface RedisResource {

	@POST
	@Path("/redis/publish")
	@Produces("application/json")
	String publish();
}
