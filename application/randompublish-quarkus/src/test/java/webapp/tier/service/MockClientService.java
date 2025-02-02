package webapp.tier.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/quarkus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MockClientService {

	@POST
	@Path("/activemq/publish")
	@Produces("application/json")
	public String activemq() {
		return "Test";
	}

	@POST
	@Path("/hazelcast/publish")
	@Produces("application/json")
	public String hazelcast() {
		return "Test";
	}

	@POST
	@Path("/mongodb/insert")
	@Produces("application/json")
	public String mongodb() {
		return "Test";
	}

	@POST
	@Path("/postgres/insert")
	@Produces("application/json")
	public String postgres() {
		return "Test";
	}

	@POST
	@Path("/rabbitmq/publish")
	@Produces("application/json")
	public String rabbitmq() {
		return "Test";
	}

	@POST
	@Path("/redis/publish")
	@Produces("application/json")
	public String redis() {
		return "Test";
	}
}
