package webapp.tier.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/random")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MockDeliverService {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@GET
	public String random() {
		logger.log(Level.INFO, "Received rest request form consumer");
		return "Test";
	}
}
