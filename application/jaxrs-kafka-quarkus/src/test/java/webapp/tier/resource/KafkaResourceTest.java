package webapp.tier.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.UniEmitter;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
class KafkaResourceTest {

	@InjectKafkaCompanion
	KafkaCompanion companion;

	String testBody = "Test Message";
	String message = "Hello k8s-3tier-webapp with quarkus";

	@Test
	void testPublish() {
		String body = given()
				.when()
				.post("/quarkus/kafka/publish")
				.then()
				.statusCode(200)
				.extract().body()
				.asString();
		assertThat(body, containsString(message));
	}

	@Test
	void testSubscreibe() {
		companion.produceStrings()
				.usingGenerator(i -> new ProducerRecord<>("message", testBody));

		Client client = ClientBuilder.newClient();
		WebTarget target = client
				.target("http://localhost:" + port + "/quarkus/kafka/subscribe");
		try (SseEventSource eventSource = SseEventSource.target(target).build()) {
			Uni<List<String>> petList = Uni.createFrom()
					.emitter(new Consumer<UniEmitter<? super List<String>>>() {
						@Override
						public void accept(UniEmitter<? super List<String>> uniEmitter) {
							List<String> messageList = new CopyOnWriteArrayList<>();
							eventSource.register(event -> {
								messageList.add(testBody);
								if (messageList.size() == 5) {
									uniEmitter.complete(messageList);
								}
							}, ex -> {
								uniEmitter.fail(
										new IllegalStateException("SSE failure", ex));
							});
							eventSource.open();

						}
					});
			List<String> messages = petList.await().atMost(Duration.ofMinutes(1));
			assertThat(messages.size(), greaterThanOrEqualTo(5));
			assertEquals(testBody, messages.get(0));
			assertEquals(testBody, messages.get(1));
			assertEquals(testBody, messages.get(2));
			assertEquals(testBody, messages.get(3));
			assertEquals(testBody, messages.get(4));
		}
	}
}
