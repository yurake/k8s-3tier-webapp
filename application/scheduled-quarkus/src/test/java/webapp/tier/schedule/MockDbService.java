package webapp.tier.schedule;

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
public class MockDbService {

	@POST
	@Path("/postgres/delete")
	public String deletePostgres() {
		return "Test";
	}

	@POST
	@Path("/mysql/delete")
	public String deleteMysql() {
		return "Test";
	}

	@POST
	@Path("/mongodb/delete")
	public String deleteMongodb() {
		return "Test";
	}
}
