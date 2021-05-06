package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MysqlResourceErrorTest {

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
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
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
