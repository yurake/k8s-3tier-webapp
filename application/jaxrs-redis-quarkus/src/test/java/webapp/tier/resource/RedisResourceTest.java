package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class RedisResourceTest {

	@ConfigProperty(name = "common.message")
	String message;

	@Test
	void testPut() {
		given()
				.accept(ContentType.JSON)
				.when()
				.contentType("application/json")
				.post("/quarkus/redis/put")
				.then()
				.statusCode(200)
				.body(containsString(message));
	}

	@Test
	void testGet() {
		given()
				.accept(ContentType.JSON)
				.when()
				.get("/quarkus/redis/get")
				.then()
				.statusCode(200)
				.body(containsString("No Data."));
	}

	@Test
	void testDelete() {
		given()
				.accept(ContentType.JSON)
				.when()
				.delete("/quarkus/redis/delete")
				.then()
				.statusCode(200)
				.body(containsString("Delete"));
	}
	
	@Test
	void testPublish() {
		given()
				.accept(ContentType.JSON)
				.when()
				.contentType("application/json")
				.post("/quarkus/redis/publish")
				.then()
				.statusCode(200)
				.body(containsString(message));
	}
}
