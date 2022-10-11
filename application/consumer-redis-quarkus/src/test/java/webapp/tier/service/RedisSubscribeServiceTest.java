package webapp.tier.service;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class RedisSubscribeServiceTest {

	@Inject
	MockPublisher mockpub;

	@Test
	void testSubscribe() {
		MsgBean msgbean = new MsgBean(0, "Test Message", "Test");
		mockpub.set("testId", msgbean);
	}
}
