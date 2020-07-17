package webapp.tier.service;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class KafkaSubscribeServiceTest {

	@Test
	void test() {
		KafkaSubscribeService svc = new KafkaSubscribeService();
		try {
		svc.process("Test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
