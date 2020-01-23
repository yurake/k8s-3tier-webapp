package org.acme.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/random")
@RegisterRestClient(configKey = "random")
public interface DeliverService {

	@GET
	@Produces("application/json")
	String random();

}
