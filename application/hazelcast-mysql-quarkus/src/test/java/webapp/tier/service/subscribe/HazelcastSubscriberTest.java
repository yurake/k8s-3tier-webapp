package webapp.tier.service.subscribe;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import com.hazelcast.core.Message;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@QuarkusTest
class HazelcastSubscriberTest {

	@Test
	void testOnMessageError() throws NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), "test msg");
		String body = msgbean.createBody(msgbean, ",");
		Message<String> message = new Message<String>("topicName", body, 0, null);

		HazelcastSubscriber hassub = new HazelcastSubscriber();
		try {
			hassub.onMessage(message);
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
		}
	}

}
