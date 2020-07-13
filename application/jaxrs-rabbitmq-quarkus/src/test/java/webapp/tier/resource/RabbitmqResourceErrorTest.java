package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitmqResourceErrorTest {

	@BeforeAll
	public static void setup() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		try {
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

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