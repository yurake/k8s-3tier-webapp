package webapp.tier.service.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.protobuf.Empty;

import io.quarkus.grpc.GrpcService;
import webapp.tier.grpc.IdGrpc;
import webapp.tier.grpc.MsgGrpc;

@Singleton
public class WebappClientService {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Inject
	@GrpcService
	IdGrpc.IdBlockingStub idService;

	@Inject
	@GrpcService
	MsgGrpc.MsgBlockingStub msgclient;

	public int getId() {
		int rc = 0;
		try {
			rc = idService.getId(Empty.newBuilder().build()).getId();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Create id Error.", e);
			throw e;
		}
		return rc;
	}

	public String getMsg() {
		String rc = "Error";
		try {
			rc = msgclient.getMsg(Empty.newBuilder().build()).getMessage();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Create msg Error.", e);
			throw e;
		}
		return rc;
	}
}
