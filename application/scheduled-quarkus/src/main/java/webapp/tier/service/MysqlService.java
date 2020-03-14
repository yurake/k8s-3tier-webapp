package webapp.tier.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/quarkus")
@RegisterRestClient(configKey = "wlp")
public interface MysqlService {

	@POST
	@Path("/mysql/delete")
	@Produces("application/json")
	String delete();

}
