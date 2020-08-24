package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastSubscribeServiceTest {

	@Inject
	HazelcastSubscribeService svc;

	private static HazelcastInstance mockInstance;
	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

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
		assertThat(svc.publishMsg(mockInstance).getFullmsg(), containsString(respbody));
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
