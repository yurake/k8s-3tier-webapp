package webapp.tier.resource;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.restassured.RestAssured;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.UniEmitter;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
class KafkaResourceTest {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@InjectKafkaCompanion
	KafkaCompanion companion;

	String testBody = "Test Message";

	/**
	@Test
	void testSubscreibe() {
		companion.produceStrings()
				.usingGenerator(i -> new ProducerRecord<>("message", testBody));

		ConsumerTask<String, String> consumer = companion.consumeStrings().fromTopics("message", 10);
		consumer.awaitCompletion();
		assertEquals(10, consumer.count());

		logger.log(Level.INFO, "Generate messeage is OK.");

		given()
				.when()
				.get("/quarkus/kafka/subscribe")
				.then()
				.statusCode(200)
				.body(containsString(testBody));
	}
	**/

	@Test
	public void testSubscreibe() {
		companion.produceStrings()
				.usingGenerator(i -> new ProducerRecord<>("message", testBody));

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:" + RestAssured.port + "/quarkus/kafka/subscribe");
		try (SseEventSource eventSource = SseEventSource.target(target).build()) {
			Uni<List<String>> petList = Uni.createFrom().emitter(new Consumer<UniEmitter<? super List<String>>>() {
				@Override
				public void accept(UniEmitter<? super List<String>> uniEmitter) {
					List<String> messageList = new CopyOnWriteArrayList<>();
					eventSource.register(event -> {
						messageList.add(testBody);
						if (messageList.size() == 5) {
							uniEmitter.complete(messageList);
						}
					}, ex -> {
						uniEmitter.fail(new IllegalStateException("SSE failure", ex));
					});
					eventSource.open();

				}
			});
			List<String> messages = petList.await().atMost(Duration.ofMinutes(1));
			Assertions.assertEquals(5, messages.size());
			Assertions.assertEquals(testBody, messages.get(0));
			Assertions.assertEquals(testBody, messages.get(1));
			Assertions.assertEquals(testBody, messages.get(2));
			Assertions.assertEquals(testBody, messages.get(3));
			Assertions.assertEquals(testBody, messages.get(4));
		}
	}
}
