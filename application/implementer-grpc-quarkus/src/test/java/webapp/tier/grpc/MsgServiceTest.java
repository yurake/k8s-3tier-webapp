package webapp.tier.grpc;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.grpc.stub.StreamObserver;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MsgServiceTest {

	@Inject
	MsgService msgsvc;

	@Test
	void testGetMsg() {
		@SuppressWarnings("unchecked")
		StreamObserver<MsgReply> responseObserverMock = Mockito.mock(StreamObserver.class);
		msgsvc.getMsg(null, responseObserverMock);
	}

}
