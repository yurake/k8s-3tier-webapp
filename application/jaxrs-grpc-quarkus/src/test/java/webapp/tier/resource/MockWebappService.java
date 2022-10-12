package webapp.tier.resource;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import webapp.tier.grpc.Id;
import webapp.tier.grpc.IdReply;
import webapp.tier.grpc.Msg;
import webapp.tier.grpc.MsgReply;

@GrpcService
public class MockWebappService implements Id, Msg {

	@Override
	public Uni<MsgReply> getMsg(Empty request) {
		return Uni.createFrom().item(() -> MsgReply.newBuilder().setMessage("test").build());
	}

	@Override
	public Uni<IdReply> getId(Empty request) {
		return Uni.createFrom().item(() -> IdReply.newBuilder().setId(11111).build());
	}

}
