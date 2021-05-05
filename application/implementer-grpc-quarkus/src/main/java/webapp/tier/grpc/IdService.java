package webapp.tier.grpc;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import io.grpc.stub.StreamObserver;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@Singleton
public class IdService extends IdGrpc.IdImplBase {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public void getId(IdRequest request, StreamObserver<IdReply> responseObserver) {
		try {
			int id = CreateId.createid();
			logger.log(Level.INFO, "Return id: " + MsgUtils.intToString(id));
			responseObserver.onNext(IdReply.newBuilder().setId(id).build());
			responseObserver.onCompleted();
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Create Id Error.", e);
			responseObserver.onError(e);
		}
	}
}
