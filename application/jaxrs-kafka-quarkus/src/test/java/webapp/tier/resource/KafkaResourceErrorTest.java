package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.KafkaService;

@QuarkusTest
class KafkaResourceErrorTest {

	@InjectMock
	private KafkaService svc;

	@Test
	void testSubscribeError() {
		given()
				.when()
				.get("/quarkus/kafka/subscribe")
				.then()
				.statusCode(200)
				.body(containsString(""));
	}

//	@Test
//	void testPublishError() throws NoSuchAlgorithmException {
//		when(svc.publishMsg()).thenThrow(new NoSuchAlgorithmException());
//		given()
//				.when()
//				.contentType("application/json")
//				.post("/quarkus/kafka/publish")
//				.then()
//				.statusCode(500);
//	}
}
