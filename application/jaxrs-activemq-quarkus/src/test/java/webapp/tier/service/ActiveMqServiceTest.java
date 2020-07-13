package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActiveMqServiceTest {

	@Inject
	ActiveMqService svc;

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testPutMsg() {
		try {
			MsgBean msgbean = svc.putMsg();
			assertThat(msgbean.getFullmsg(), containsString(respbody));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testGetMsg() {
		try {
			MsgBean msgbeanexpect = svc.putMsg();
			MsgBean msgbeanactual = svc.getMsg();
			assertThat(msgbeanactual.getId(), is(msgbeanexpect.getId()));
			assertThat(msgbeanactual.getMessage(), is(msgbeanexpect.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testPublishMsg() {
		try {
			MsgBean msgbean = svc.publishMsg();
			assertThat(msgbean.getFullmsg(), containsString(respbody));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	@Test
	void testStartStopSubscribe() {
		try {
			ActiveMqService.stopReceived();
			ActiveMqService.startReceived();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
