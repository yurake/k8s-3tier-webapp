package webapp.tier.grpc;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class MsgService implements Msg {
	
	@ConfigProperty(name = "common.message")
	String message;

	@Override
	public Uni<MsgReply> getMsg(Empty request) {
		return Uni.createFrom().item(() -> MsgReply.newBuilder().setMessage(message).build());
	}

}
