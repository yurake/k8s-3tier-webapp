package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.ActiveMqService;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActivemqResourceErrorTest {

	@InjectMock
	private ActiveMqService svc;

	@Test
	void testPutError() throws NoSuchAlgorithmException {
		when(svc.putMsg()).thenThrow(new NoSuchAlgorithmException());
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/put")
				.then()
				.statusCode(500);
	}

	@Test
	void testGetError() {
		when(svc.getMsg()).thenThrow(new RuntimeException());
		given()
				.when()
				.get("/quarkus/activemq/get")
				.then()
				.statusCode(500);
	}

	@Test
	void testPublishError() throws NoSuchAlgorithmException {
		when(svc.publishMsg()).thenThrow(new NoSuchAlgorithmException());
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/publish")
				.then()
				.statusCode(500);
	}
}
