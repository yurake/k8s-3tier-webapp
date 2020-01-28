package webapp.tier.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "random")
public interface DeliverService {

	@POST
	@Path("/activemq/publish")
	@Produces("application/json")
	String activemq();


	@POST
	@Path("/rabbitmq/publish")
	@Produces("application/json")
	String rabbitmq();


	@POST
	@Path("/redis/publish")
	String redis();

	@POST
	@Path("/postgres/insert")
	String postgres();

}
