package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MemcachedResourceTest {

	@Test
	void testSetError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/memcached/set")
				.then()
				.statusCode(200);
	}

	@Test
	void testGetError() {
		given()
				.when()
				.get("/quarkus/memcached/get")
				.then()
				.statusCode(200)
				.body(is("Received id: null, msg: null"));
	}

}

