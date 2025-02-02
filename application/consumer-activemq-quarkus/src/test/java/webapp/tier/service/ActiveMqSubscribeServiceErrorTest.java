package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Session;
import jakarta.inject.Inject;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActiveMqSubscribeServiceErrorTest {

	@Inject
	ConnectionFactory connectionFactory;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "activemq.split.key")
	String splitkey;
	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private static final String respbody = "Test Response OK from Mock";

	private static MockDeliverService mock = null;

	@BeforeAll
	static void init() {
		mock = mock(MockDeliverService.class);
	}

	@BeforeEach
	void setup() {
		clearInvocations(mock);
	}

	@AfterEach
	void tearDown() {
		reset(mock);
	}

	@Test
	void testSubscribeError() {
		when(mock.random()).thenThrow(new RuntimeException("Rest response error"));
		QuarkusMock.installMockForType(mock, MockDeliverService.class);

		try {
			MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
			String body = MsgUtils.createBody(msgbean, splitkey);
			try (JMSContext context = connectionFactory
					.createContext(Session.AUTO_ACKNOWLEDGE)) {
				context.createProducer().send(context.createTopic(topicname),
						context.createTextMessage(body));
			}
			Thread.sleep(1000);
			assertThat(msgbean.getFullmsg(), not(containsString(respbody)));
			verify(mock, atLeastOnce()).random();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
