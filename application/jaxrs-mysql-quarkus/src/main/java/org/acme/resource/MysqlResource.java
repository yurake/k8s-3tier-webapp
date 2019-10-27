package org.acme.resource;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.service.MysqlService;
import org.acme.util.json.FullMassage;

@Path("/mysql")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MysqlResource {

	@Inject
	MysqlService mysqlsvc;


	@POST
    public String insert() {
		return mysqlsvc.insertMysql();
    }

	@GET
    public Set<FullMassage> select() {
		return mysqlsvc.selectMysql();
    }
}
