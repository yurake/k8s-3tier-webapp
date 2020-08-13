package webapp.tier.service;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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