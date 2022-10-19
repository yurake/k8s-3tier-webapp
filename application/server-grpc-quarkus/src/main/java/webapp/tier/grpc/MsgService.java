package webapp.tier.grpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class MsgService implements Msg {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@ConfigProperty(name = "common.message")
	String message;

	@Override
	public Uni<MsgReply> getMsg(Empty request) {
		return Uni.createFrom()
				.item(() -> MsgReply.newBuilder().setMessage(message).build())
				.invoke(i -> logger.log(Level.INFO, "Return message"));
				}

}
