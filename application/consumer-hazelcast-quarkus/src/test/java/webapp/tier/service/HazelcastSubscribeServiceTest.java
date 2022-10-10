package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
class HazelcastSubscribeServiceTest {

	@Inject
	HazelcastSubscribeService svc;
	
	@ConfigProperty(name = "hazelcast.topic.name")
	String topicname;

	@ConfigProperty(name = "hazelcast.split.key")
	String splitkey;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static HazelcastInstance mockInstance;
	private String respbody = "message: Hello k8s-3tier-webapp with quarkus";
	private static MsgBean errormsg = new MsgBean(0, "Unexpected Error");

	@BeforeEach
	public void setup() throws IOException {
		mockInstance = Hazelcast.newHazelcastInstance();
	}

	@AfterEach
	public void after() {
		mockInstance.shutdown();
	}

	@Test
	void testSubscribeHazelcast() {
		svc.subscribeHazelcast(mockInstance, svc.createHazelcastDeliverSubscriber());
		assertThat(publishMsg(mockInstance).getFullmsg(), containsString(respbody));
	}

	private MsgBean publishMsg(HazelcastInstance mockInstance2) {
		MsgBean msgbean = errormsg;

		try {
			msgbean = new MsgBean(CreateId.createid(), respbody, "Publish");
			String body = MsgUtils.createBody(msgbean, splitkey);
			ITopic<Object> topic = mockInstance2.getTopic(topicname);
			topic.publish(body);
		} catch (IllegalStateException | NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Publish Error.", e);
			e.printStackTrace();
		} finally {
			if (mockInstance2 != null) {
				mockInstance2.shutdown();
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Test
	void testSubscribeNoData() {
		try {
			svc.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
