package webapp.tier.grpc;

import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.google.protobuf.Empty;

import io.quarkus.grpc.runtime.annotations.GrpcService;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class IdServiceTest {
	@Inject
    @GrpcService("id")
	IdGrpc.IdBlockingStub stub;

	@Inject
	IdService svc;

	@Test
	void test1() {
		stub.getId(Empty.newBuilder().build());
		fail("まだ実装されていません");
	}

	@Test
	void test2() {
		svc.getId(null, null);
		fail("まだ実装されていません");
	}

}
