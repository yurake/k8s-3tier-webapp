package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MysqlResourceTest {

	@Test
	void testselect() {
		given()
				.when().get("/quarkus/mysql/select")
				.then()
				.statusCode(500)
				.body(is(not("selected")));
	}

	@Test
	void testinsert() {
		given()
				.when().post("/quarkus/mysql/insert")
				.andReturn()
				.then()
				.statusCode(415)
				.body(is(not("inserted")));
	}

}
