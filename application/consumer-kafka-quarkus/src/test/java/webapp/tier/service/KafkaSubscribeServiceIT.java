package webapp.tier.service;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusIntegrationTest
class KafkaSubscribeServiceIT extends KafkaSubscribeServiceTest {
	
	@InjectMock
	KafkaSubscribeService svc;

	@Override
	KafkaSubscribeService getSvc() {
		return this.svc;
	}
}
