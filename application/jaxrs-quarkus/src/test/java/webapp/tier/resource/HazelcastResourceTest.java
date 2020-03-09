package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastResourceTest {

	@Test
	void testPutcacheError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/putcache")
				.then()
				.statusCode(500)
				.body(is("Set Error."));
	}

	@Test
	void testGetcacheError() {
		given()
				.when()
				.get("/quarkus/hazelcast/getcache")
				.then()
				.statusCode(500)
				.body(is("Get Error."));
	}

	@Test
	void testPutqueueError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/putqueue")
				.then()
				.statusCode(500)
				.body(is("Put Error."));
	}

	@Test
	void testGetqueueError() {
		given()
				.when()
				.get("/quarkus/hazelcast/getqueue")
				.then()
				.statusCode(500)
				.body(is("Get Error."));
	}

	@Test
	void testPublishError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/publish")
				.then()
				.statusCode(500)
				.body(is("Publish Error."));
	}

}

