package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqServiceTest {

	@Test
	void testOnStartError() {
		ThreadTestOnStartError th = new ThreadTestOnStartError();
		ActiveMqService svc = new ActiveMqService();
		try {
			th.start();
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
