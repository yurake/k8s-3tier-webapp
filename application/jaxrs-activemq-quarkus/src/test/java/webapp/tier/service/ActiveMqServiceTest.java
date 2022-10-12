package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Session;

import org.eclipse.microprofile.config.inject.ConfigProperty;
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

	@Inject
	ConnectionFactory connectionFactory;

	@ConfigProperty(name = "activemq.queue.name")
	String queuename;

	private static final String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testPutMsg() {
		try {
			MsgBean msgbean = svc.putMsg();
			assertThat(msgbean.getFullmsg(), containsString(respbody));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			svc.getMsg();
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
	void testGetMsgNoData() throws Exception {
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createProducer().send(context.createQueue(queuename),
					context.createTextMessage(null));
		}
		MsgBean msgbeanactual = svc.getMsg();
		assertThat(msgbeanactual.getFullmsg(), containsString("No Data."));
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
