package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class DeliverResourceTest {

	@Test
	void testactivemq404() {
		given()
				.when().get("/error/path")
				.then()
				.statusCode(404)
				.body("$.size()", is(1));
	}

}
