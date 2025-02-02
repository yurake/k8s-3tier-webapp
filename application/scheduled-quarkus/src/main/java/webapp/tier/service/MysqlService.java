package webapp.tier.service;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "mysql")
public interface MysqlService {

	@POST
	@Path("/mysql/delete")
	@Produces("application/json")
	String delete();

}
