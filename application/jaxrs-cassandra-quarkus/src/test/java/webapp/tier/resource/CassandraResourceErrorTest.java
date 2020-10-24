package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import com.datastax.oss.quarkus.test.CassandraTestResource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.CassandraService;

@QuarkusTest
@QuarkusTestResource(CassandraTestResource.class)
class CassandraResourceErrorTest {

	@InjectMock
	CassandraService svc;

	@Test
	void testInsertError() throws NoSuchAlgorithmException {
		when(svc.insertMsg()).thenThrow(new NoSuchAlgorithmException());
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/cassandra/insert")
				.then()
				.statusCode(500);
	}

	@Test
	void testSelectError() {
		when(svc.selectMsg()).thenThrow(new RuntimeException());
		given()
				.when()
				.get("/quarkus/cassandra/select")
				.then()
				.statusCode(500);
	}

	@Test
	void testDeleteError() {
		when(svc.deleteMsg()).thenThrow(new RuntimeException());
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/cassandra/delete")
				.then()
				.statusCode(500);
	}
}
