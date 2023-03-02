package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

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
class KafkaConverterTest {

	@InjectKafkaCompanion
	KafkaCompanion companion;

	String testBody = "Test,Message";
	String message = "Hello k8s-3tier-webapp with quarkus";

	@Test
	void testConvert() throws InterruptedException {
		ProducerTask producer = companion.produceStrings().usingGenerator(i -> new ProducerRecord<>("converter", testBody));
		ConsumerTask<String, String> consumer = companion.consumeStrings().fromTopics("message",10);
		consumer.awaitCompletion();
		producer.close();
		assertEquals(10, consumer.count());
	}
}
