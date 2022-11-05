package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.RabbitmqService;

@QuarkusTest
class RabbitmqResourceErrorTest {

	@InjectMock
	RabbitmqService svc;

	@Test
	void testPublishError() throws NoSuchAlgorithmException {
		when(svc.publishMsg()).thenThrow(new NoSuchAlgorithmException());
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/publish")
				.then()
				.statusCode(500);
	}
}