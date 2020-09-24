package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.MemcachedService;

@QuarkusTest
class MemcachedResourceErrorTest {

	@InjectMock
	MemcachedService svc;

	@Test
	void testSetError() {
		try {
			when(svc.setMsg(ArgumentMatchers.any())).thenThrow(new RuntimeException());
		} catch (NoSuchAlgorithmException | RuntimeException e) {
			e.printStackTrace();
			fail();
		}
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/memcached/set")
				.then()
				.statusCode(500);
	}

	@Test
	void testGetError() {
		when(svc.getMsg(ArgumentMatchers.any())).thenThrow(new RuntimeException());
		given()
				.when()
				.get("/quarkus/memcached/get")
				.then()
				.statusCode(500);
	}

}

