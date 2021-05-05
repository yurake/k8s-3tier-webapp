package webapp.tier.service;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class KafkaServiceTest {

	@Inject
	KafkaService kfksvc;

	String testmsg = "testmsg";
	String respbody = "Hello k8s-3tier-webapp with quarkus";

//	@Test
//	void testMessegeToMemory() {
//		String result = kfksvc.messegeToMemory(testmsg);
//		assertThat(result, is(testmsg));
//		assertThat(LatestMessage.getLatestMsg(), is(testmsg));
//	}

//	@Test
//	void testPublishMsg() {
//		try {
//			kfksvc.publishMsg();
//			TODO Assertion
//			assertThat(ChannelEmitter.getRespmsg(), containsString(respbody));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

}
