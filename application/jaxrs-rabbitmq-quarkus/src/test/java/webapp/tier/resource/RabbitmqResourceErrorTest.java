package webapp.tier.resource;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqResourceErrorTest {

	@Test
	void testPutError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/put")
				.then()
				.statusCode(500);
	}

	@Test
	void testGetError() {
		given()
				.when()
				.get("/quarkus/rabbitmq/get")
				.then()
				.statusCode(500);
	}

	@Test
	void testPublishError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/publish")
				.then()
				.statusCode(500);
	}
}