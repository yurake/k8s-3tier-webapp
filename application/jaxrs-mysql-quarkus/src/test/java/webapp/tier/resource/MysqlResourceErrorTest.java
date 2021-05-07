package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.MysqlService;

@QuarkusTest
class MysqlResourceErrorTest {

	@Inject
	MysqlService svc;

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
		svc.invalidateCache();
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
