package webapp.tier.grpc;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ConsumerWebappResourceTest {

	String expectMsg = "Hello k8s-3tier-webapp with quarkus";

	@Test
	public void testGetId() {
		String response = get("/grpc/id").asString();
		assertThat(response.length(), is(5));
	}

	@Test
	public void testGetMsg() {
		String response = get("/grpc/msg").asString();
		assertThat(response, is(expectMsg));
	}
}
