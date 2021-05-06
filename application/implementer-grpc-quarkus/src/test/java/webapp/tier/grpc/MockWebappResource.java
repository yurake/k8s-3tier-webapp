package webapp.tier.grpc;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.protobuf.Empty;

import io.quarkus.grpc.runtime.annotations.GrpcService;

@ApplicationScoped
@Path("/grpc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MockWebappResource {

	@Inject
	@GrpcService("webapp")
	IdGrpc.IdBlockingStub idService;

	@Inject
	@GrpcService("webapp")
	MsgGrpc.MsgBlockingStub msgclient;

	@GET
	@Path("/id")
	public int getId() {
		return idService.getId(Empty.newBuilder().build()).getId();
	}

	@GET
	@Path("/msg")
	public String getMsg() {
		return msgclient.getMsg(Empty.newBuilder().build()).getMessage();
	}
}
