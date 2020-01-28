package webapp.tier.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "wlp")
public interface PostgresService {

	@POST
	@Path("/postgres/delete")
	@Produces("application/json")
	public String delete();

}
