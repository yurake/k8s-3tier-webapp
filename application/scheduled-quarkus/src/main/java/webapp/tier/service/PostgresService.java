package webapp.tier.service;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "postgres")
public interface PostgresService {

	@POST
	@Path("/postgres/delete")
	@Produces("application/json")
	public String delete();

}
