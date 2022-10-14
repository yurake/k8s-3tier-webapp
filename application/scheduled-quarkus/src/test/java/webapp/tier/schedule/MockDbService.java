package webapp.tier.schedule;

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