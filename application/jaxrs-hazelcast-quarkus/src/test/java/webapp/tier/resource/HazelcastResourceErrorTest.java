package webapp.tier.resource;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastResourceErrorTest {

	@Test
	void testSetError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/setcache")
				.then()
				.statusCode(500);
	}

	@Test
	void testGetCacheError() {
		given()
				.when()
				.get("/quarkus/hazelcast/getcache")
				.then()
				.statusCode(500);
	}

	@Test
	void testPutError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/putqueue")
				.then()
				.statusCode(500);
	}

	@Test
	void testGetQueueError() {
		given()
				.when()
				.get("/quarkus/hazelcast/getqueue")
				.then()
				.statusCode(500);
	}

	@Test
	void testPublishError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/hazelcast/publish")
				.then()
				.statusCode(500);
	}

}
