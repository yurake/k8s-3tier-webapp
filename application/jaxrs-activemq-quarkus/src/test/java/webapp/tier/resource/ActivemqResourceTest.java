package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActivemqResourceTest {

	private String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testPut() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/put")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}

	@Test
	void testGet() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/put");
		given()
				.when()
				.get("/quarkus/activemq/get")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}

	@Test
	void testPublish() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/activemq/publish")
				.then()
				.statusCode(200)
				.body(containsString(respbody));
	}
}
