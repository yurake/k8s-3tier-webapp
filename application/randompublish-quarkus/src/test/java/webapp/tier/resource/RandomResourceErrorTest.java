package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import webapp.tier.service.RandomService;

@QuarkusTest
class RandomResourceErrorTest {

	@InjectMock
	RandomService svc;
	
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
