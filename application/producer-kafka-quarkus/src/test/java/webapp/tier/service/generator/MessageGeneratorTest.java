package webapp.tier.service.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
class MessageGeneratorTest {

	@InjectKafkaCompanion
	KafkaCompanion companion;

	@Test
	void testGenerate() {
		ConsumerTask<String, String> consumer = companion.consumeStrings()
				.fromTopics("message", 2);
		consumer.awaitCompletion();
		assertEquals(2, consumer.count());
	}

}
