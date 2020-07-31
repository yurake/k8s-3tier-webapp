package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActiveMqSubscribeServiceTest {

	@Inject
	ActiveMqSubscribeService svc;

	@Test
	void testStartStopSubscribe() {
		try {
			ActiveMqSubscribeService.stopReceived();
			ActiveMqSubscribeService.startReceived();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
