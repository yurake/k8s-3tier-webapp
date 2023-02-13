package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import io.smallrye.reactive.messaging.kafka.companion.ProducerTask;

@QuarkusIntegrationTest
class KafkaSubscribeServiceIT extends KafkaSubscribeBase {
	
	@InjectMock
	KafkaSubscribeService svc;

	@InjectKafkaCompanion
	KafkaCompanion companion;

	@Test
	@Override
	void testProcess() {
		try {
			getSvc().process("Test");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	@Override
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
	
	@Override
	KafkaSubscribeService getSvc() {
		return this.svc;
	}
}
