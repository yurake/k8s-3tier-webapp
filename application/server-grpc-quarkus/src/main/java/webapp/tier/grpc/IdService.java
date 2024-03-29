package webapp.tier.grpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import webapp.tier.util.CreateId;

@GrpcService
public class IdService implements Id {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	int id;

	@Override

	public Uni<IdReply> getId(Empty request) {
		return Uni.createFrom().item(1).onItem()
				.transform(Unchecked.function(i -> {
					return IdReply.newBuilder().setId(CreateId.createid()).build();
				}))
				.invoke(i -> logger.log(Level.INFO, "Return id"))
				.log();
	}
}
