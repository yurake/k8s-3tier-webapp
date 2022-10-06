package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.RandomService;

@QuarkusTest
class RandomResourceTest {

	@InjectMock
	RandomService svc;

	@Test
	void testrandom() throws Exception {
        when(svc.deliverrandom(ArgumentMatchers.anyInt())).thenReturn("Test");
		given()
				.when().get("/random")
				.then()
				.statusCode(200)
				.contentType(MediaType.APPLICATION_JSON)
				.body(is("Test"));
	}

	@Test
	void testrandomError() throws Exception {
        when(svc.deliverrandom(ArgumentMatchers.anyInt())).thenThrow(new IllegalArgumentException("Test Error"));
		given()
				.when().get("/random")
				.then()
				.statusCode(500)
				.body(is("Test Error"));
	}
}
