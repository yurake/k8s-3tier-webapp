package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqResourceTest {

	@ConfigProperty(name = "common.message")
	String message;

	@Test
	void testPublish() throws Exception {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/publish")
				.then()
				.statusCode(200)
				.body(containsString(message));
	}

}