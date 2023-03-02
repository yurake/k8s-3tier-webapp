package webapp.tier.resource;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class RandomResourceIT {
	
	@Test
	void testrandom() throws Exception {
		given()
				.when().get("/random")
				.then()
				.statusCode(500);
	}

}
