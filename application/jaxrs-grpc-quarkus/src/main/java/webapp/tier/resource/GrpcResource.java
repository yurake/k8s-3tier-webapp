package webapp.tier.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import webapp.tier.grpc.Id;
import webapp.tier.grpc.Msg;

@Path("/quarkus/grpc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GrpcResource {

	@GrpcClient
	Id id;

	@GrpcClient
	Msg msg;

	@GET
	@Path("/getid")
	public Uni<Object> getId() {
		return id.getId(Empty.newBuilder().build())
				.onItem().transform(idReply -> idReply.getId());
	}

	@GET
	@Path("/getmsg")
	public Uni<String> getMsg() {
		return msg.getMsg(Empty.newBuilder().build())
				.onItem().transform(msgReply -> msgReply.getMessage());
	}
}
