package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqResourceTest {

	@Test
	void testPutError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/put")
				.then()
				.statusCode(500)
				.body(is("Put Error."));
	}

	@Test
	void testGetError() {
		given()
				.when()
				.get("/quarkus/rabbitmq/get")
				.then()
				.statusCode(500)
				.body(is("Get Error."));
	}

	@Test
	void testPublishError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/publish")
				.then()
				.statusCode(500)
				.body(is("Publish Error."));
	}

}

