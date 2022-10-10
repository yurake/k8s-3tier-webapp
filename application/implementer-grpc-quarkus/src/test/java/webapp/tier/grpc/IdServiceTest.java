package webapp.tier.grpc;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.grpc.stub.StreamObserver;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class IdServiceTest {

	@Inject
	IdService idsvc;

	@Test
	public void testGetId() {
		@SuppressWarnings("unchecked")
		StreamObserver<IdReply> responseObserverMock = Mockito.mock(StreamObserver.class);
		idsvc.getId(null, responseObserverMock);
	}
}
