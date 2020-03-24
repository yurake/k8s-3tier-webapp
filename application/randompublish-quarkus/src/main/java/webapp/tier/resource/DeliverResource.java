package webapp.tier.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/quarkus")
@RegisterRestClient(configKey = "random")
public interface DeliverResource {

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


	@POST
	@Path("/hazelcast/publish")
	String hazelcast();

}
