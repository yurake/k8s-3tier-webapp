package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RabbitMqServiceTest {

	@Test
	void testIsActiveError() {
		RabbitMqService svc = new RabbitMqService();
		assertThat(svc.isActive(), is(false));
	}

}
