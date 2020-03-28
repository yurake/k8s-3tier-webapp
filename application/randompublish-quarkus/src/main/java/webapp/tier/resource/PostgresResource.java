package webapp.tier.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "postgres")
public interface PostgresResource {

	@POST
	@Path("/postgres/insert")
	String insert();
}
