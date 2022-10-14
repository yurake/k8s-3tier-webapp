package webapp.tier.resource;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MongodbResourceErrorTest {

	@Test
	void testInsertError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mongodb/insert")
				.then()
				.statusCode(500);
	}

	@Test
	void testSelectError() {
		given()
				.when()
				.get("/quarkus/mongodb/select")
				.then()
				.statusCode(500);
	}

	@Test
	void testDeleteError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mongodb/delete")
				.then()
				.statusCode(500);
	}

}
