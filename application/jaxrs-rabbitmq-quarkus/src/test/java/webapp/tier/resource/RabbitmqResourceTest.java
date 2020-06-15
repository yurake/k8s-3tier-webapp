package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.RabbitmqService;

@QuarkusTest
class RabbitmqResourceTest {

	@Test
	void testPutError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		try {
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/put")
				.then()
				.statusCode(500)
				.body(is("Put Error."));
	}

	@Test
	void testGetError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		try {
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		given()
				.when()
				.get("/quarkus/rabbitmq/get")
				.then()
				.statusCode(500)
				.body(is("Get Error."));
	}

	@Test
	void testPublishError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		try {
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/rabbitmq/publish")
				.then()
				.statusCode(500)
				.body(is("Publish Error."));
	}

}

class ThreadTestOnStartError extends Thread {

	private static final Logger LOG = Logger.getLogger(ThreadTestOnStartError.class.getSimpleName());

	@Override
	public void run() {
		RabbitmqService.stopReceived();
		LOG.log(Level.INFO, "Stopped Received");
	}
}