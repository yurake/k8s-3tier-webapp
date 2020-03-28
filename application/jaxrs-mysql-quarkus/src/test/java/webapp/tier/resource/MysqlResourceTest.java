package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MysqlResourceTest {

	@Test
	void testInsertError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mysql/insert")
				.then()
				.statusCode(500)
				.body(is("Insert Error."));
	}

	@Test
	void testSelectError() {
		given()
				.when()
				.get("/quarkus/mysql/select")
				.then()
				.statusCode(500)
				.body(is("Select Error."));
	}

	@Test
	void testDeleteError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/mysql/delete")
				.then()
				.statusCode(500)
				.body(is("Delete Error."));
	}

}

