package webapp.tier.grpc;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import webapp.tier.util.CreateId;

@GrpcService
public class IdService implements Id {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	int id;

	@Override
	public Uni<IdReply> getId(Empty request) {
		try {
			id = CreateId.createid();
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Create ID Error.", e);
		}
		return Uni.createFrom().item(() -> IdReply.newBuilder().setId(id).build());
	}
}
