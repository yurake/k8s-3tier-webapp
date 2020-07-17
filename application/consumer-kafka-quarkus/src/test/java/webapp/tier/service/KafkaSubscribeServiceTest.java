package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class KafkaSubscribeServiceTest {

	@Test
	void test() {
		KafkaSubscribeService svc = new KafkaSubscribeService();
		try {
		svc.process("Test");
		fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
