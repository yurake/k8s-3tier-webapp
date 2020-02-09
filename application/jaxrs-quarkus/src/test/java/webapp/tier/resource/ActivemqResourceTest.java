package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActivemqResourceTest {

	@Test
	void testPutError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/put")
				.then()
				.statusCode(200)
				.body(is(""));
	}

	@Test
	void testGetError() {
		given()
				.when()
				.get("/quarkus/activemq/get")
				.then()
				.statusCode(200)
				.body(is(""));
	}

	@Test
	void testPublishError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/publish")
				.then()
				.statusCode(200)
				.body(is(""));
	}

}

