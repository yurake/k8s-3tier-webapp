package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActivemqResourceTest {

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testPutError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/put")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}

	@Test
	void testGetError() {
		given()
				.when()
				.get("/quarkus/activemq/get")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}

	@Test
	void testPublishError() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/publish")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}

}
