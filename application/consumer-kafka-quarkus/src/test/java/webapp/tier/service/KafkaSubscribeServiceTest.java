
package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.inject.Inject;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import io.smallrye.reactive.messaging.kafka.companion.ProducerTask;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
class KafkaSubscribeServiceTest {

	@Inject
	KafkaSubscribeService svc;

	@InjectKafkaCompanion
	KafkaCompanion companion;

	@Test
	void testProcess() {
		try {
			svc.process("Test");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testProcessKafkaCompanion() {
		ProducerTask producer = companion.produceStrings()
				.usingGenerator(i -> new ProducerRecord<>("message", "Test Message"));

		ConsumerTask<String, String> consumer = companion.consumeStrings().fromTopics(
				"message",
				10);
		consumer.awaitCompletion();
		producer.close();
		assertEquals(10, consumer.count());
	}
}
