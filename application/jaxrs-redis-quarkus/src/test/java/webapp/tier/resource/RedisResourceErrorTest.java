package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import webapp.tier.service.RedisService;

@QuarkusTest
class RedisResourceErrorTest {

	@ConfigProperty(name = "common.message")
	String message;

	@InjectMock
	RedisService svc;

	@Test
	void testPutError() throws NoSuchAlgorithmException {
		when(svc.putMsg()).thenThrow(new RuntimeException());
		given()
				.accept(ContentType.JSON)
				.when()
				.contentType("application/json")
				.post("/quarkus/redis/put")
				.then()
				.statusCode(500);
	}

	@Test
	void testGetError() {
		when(svc.getMsgList()).thenThrow(new RuntimeException());
		given()
				.accept(ContentType.JSON)
				.when()
				.get("/quarkus/redis/get")
				.then()
				.statusCode(500);
	}

	@Test
	void testDeleteError() {
		when(svc.delete()).thenThrow(new RuntimeException());
		given()
				.accept(ContentType.JSON)
				.when()
				.delete("/quarkus/redis/delete")
				.then()
				.statusCode(500);
	}
	
	@Test
	void testPublishError() throws NoSuchAlgorithmException {
		when(svc.publish()).thenThrow(new RuntimeException());
		given()
				.accept(ContentType.JSON)
				.when()
				.contentType("application/json")
				.post("/quarkus/redis/publish")
				.then()
				.statusCode(500);
	}
}
