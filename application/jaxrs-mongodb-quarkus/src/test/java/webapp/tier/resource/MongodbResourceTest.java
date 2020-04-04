package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MongodbResourceTest {

	@Test
	void testInsertError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mongodb/insert")
				.then()
				.statusCode(500)
				.body(is("Insert Error."));
	}

	@Test
	void testSelectError() {
		given()
				.when()
				.get("/quarkus/mongodb/select")
				.then()
				.statusCode(500)
				.body(is("Select Error."));
	}

	@Test
	void testDeleteError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mongodb/delete")
				.then()
				.statusCode(500)
				.body(is("Delete Error."));
	}

}

