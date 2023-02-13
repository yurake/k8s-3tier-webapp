package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class GrpcResourceIT {

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
	void testGetId() {
		given()
				.when()
				.contentType("application/json")
				.get("/quarkus/grpc/getid")
				.then()
				.statusCode(500)
				.body(is("11111"));
	}

	@Test
	void testGetMsg() {
		given()
				.when()
				.contentType("application/json")
				.get("/quarkus/grpc/getmsg")
				.then()
				.statusCode(500)
				.body(containsString("test"));
	}
}
