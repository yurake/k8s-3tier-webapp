package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.LatestMessage;

@QuarkusTest
class KafkaServiceTest {

	@Inject
	KafkaService kfksvc;

	String testmsg = "testmsg";
	String respbody = "Hello k8s-3tier-webapp with quarkus";

	@Test
	void testMessegeToMemory() {
		String result = kfksvc.messegeToMemory(testmsg);
		assertThat(result, is(testmsg));
		assertThat(LatestMessage.getLatestMsg(), is(testmsg));
	}

	@Test
	void testPublishMsg() {
		try {
			kfksvc.publishMsg();
			Thread.sleep(1000);
			assertThat(ChannelEmitter.getRespmsg(), containsString(respbody));
		} catch (NoSuchAlgorithmException | InterruptedException e) {
			e.printStackTrace();
			fail();
		}
	}

}
