package webapp.tier.grpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;

@Singleton
public class MsgService extends MsgGrpc.MsgImplBase {

	@ConfigProperty(name = "common.message")
	String message;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Override
	public void getMsg(Empty request, StreamObserver<MsgReply> responseObserver) {
		logger.log(Level.INFO, "Return msg: " + message);
		responseObserver.onNext(MsgReply.newBuilder().setMessage(message).build());
		responseObserver.onCompleted();
	}

}
