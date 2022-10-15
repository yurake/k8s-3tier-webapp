package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

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
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
@QuarkusTestResource(ArtemisTestResource.class)
class ActiveMqSubscribeServiceTest {

	@Inject
	ConnectionFactory connectionFactory;

	@ConfigProperty(name = "common.message")
	String message;
	@ConfigProperty(name = "activemq.split.key")
	String splitkey;
	@ConfigProperty(name = "activemq.topic.name")
	String topicname;

	private static final String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@Test
	void testSubscribe() {
		try {
			MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
			String body = MsgUtils.createBody(msgbean, splitkey);
			try (JMSContext context = connectionFactory
					.createContext(Session.AUTO_ACKNOWLEDGE)) {
				context.createProducer().send(context.createTopic(topicname),
						context.createTextMessage(body));
			}
			Thread.sleep(1000);
			assertThat(msgbean.getFullmsg(), containsString(respbody));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
