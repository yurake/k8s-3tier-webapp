package webapp.tier.service.client;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import webapp.tier.grpc.Id;
import webapp.tier.grpc.IdReply;
import webapp.tier.grpc.Msg;
import webapp.tier.grpc.MsgReply;

@GrpcService
public class MessageClientService implements Id, Msg {

	@Override
	public Uni<MsgReply> getMsg(Empty request) {
		return Uni.createFrom().item(() -> MsgReply.newBuilder().build());
	}

	@Override
	public Uni<IdReply> getId(Empty request) {
		return Uni.createFrom().item(() -> IdReply.newBuilder().build());
	}

}
