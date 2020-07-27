package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastMqServiceTest {

	@Inject
	HazelcastMqService svc;

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
	void testPutQueueHazelcast() {
		try {
			svc.putMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testPutQueueHazelcastErrorNull() {
		try {
			HazelcastInstance mockInstanceError = null;
			svc.putMsg(mockInstanceError);
			fail();
		} catch (Exception expected) {
			expected.printStackTrace();
		}
	}

	@Test
	void testPutQueueHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getQueue(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.putMsg(mockInstanceError).getFullmsg(), containsString(respbody));
	}

	@Test
	void testGetQueueHazelcast() {
		try {
			svc.getMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testGetQueueHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getQueue(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.getMsg(mockInstanceError).getMessage(), is("Unexpected Error"));
	}

	@Test
	void testPublishHazelcast() {
		try {
			svc.publishMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testPublishHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getTopic(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.publishMsg(mockInstanceError).getFullmsg(), containsString(respbody));
	}

	@Test
	void testSubscribeError() {
		try {
			svc.publishMsg(mockInstance);
			svc.run();
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}
}
