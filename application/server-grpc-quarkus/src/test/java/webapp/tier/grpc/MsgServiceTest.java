package webapp.tier.grpc;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MsgServiceTest {

	String message = "Hello k8s-3tier-webapp with quarkus";

	private ManagedChannel channel;

	@BeforeEach
	public void init() {
		channel = ManagedChannelBuilder.forAddress("localhost", 9001).usePlaintext()
				.build();
	}

	@AfterEach
	public void cleanup() throws InterruptedException {
		channel.shutdown();
		channel.awaitTermination(10, TimeUnit.SECONDS);
	}

	@Test
	void testGetMsg() {
		MsgReply reply = MutinyMsgGrpc.newMutinyStub(channel)
				.getMsg(Empty.newBuilder().build())
				.await().atMost(Duration.ofSeconds(5));
		assertThat(reply.getMessage(), is(message));
	}

}
