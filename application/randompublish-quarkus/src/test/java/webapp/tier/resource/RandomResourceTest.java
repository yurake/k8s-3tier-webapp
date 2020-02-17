package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RandomResourceTest {

	@Test
	void testrandom() {
		given()
				.when().get("/random")
				.then()
				.statusCode(200)
				.contentType(MediaType.APPLICATION_JSON)
				.body(is("RESTEASY004655: Unable to invoke request: java.net.UnknownHostException: jaxrs-quarkus"));
		;
	}

}
