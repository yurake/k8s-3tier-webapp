package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class HazelcastMqServiceTest {

	@Inject
	private HazelcastMqService svc;

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
	void testPutQueueHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getQueue(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.putMsg(mockInstanceError).getFullmsg(), containsString(respbody));
	}

	@Test
	void testPutQueueHazelcastNullError() {
		HazelcastInstance mockInstanceError = null;
		assertThrows(NullPointerException.class, () -> {
			svc.putMsg(mockInstanceError);
		});
	}

	@Test
	void testGetQueueList0() {
		assertThat(svc.getMsg(mockInstance).getFullmsg(), containsString("No Data."));
	}

	@Test
	void testGetQueueHazelcast() {
		MsgBean expected = svc.putMsg(Hazelcast.newHazelcastInstance());
		MsgBean msgbean = svc.getMsg(mockInstance);
		assertThat(msgbean.getFullmsg(), containsString(respbody));
		assertThat(msgbean.getId(), is(expected.getId()));
	}

	@Test
	void testGetQueueHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getQueue(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.getMsg(mockInstanceError).getMessage(), is("Unexpected Error"));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetQueueHazelcastObjectNullError() {
		HazelcastInstance mocknull = Mockito.mock(HazelcastInstance.class);
		IQueue<Object> mockqueue = Mockito.mock(IQueue.class);
		when(mocknull.getQueue(ArgumentMatchers.any())).thenReturn(mockqueue);
		when(mockqueue.poll()).thenReturn(null);
		assertThat(svc.getMsg(mocknull).getFullmsg(), containsString("No Data."));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetQueueHazelcastObjectEmptyError() {
		HazelcastInstance mocknull = Mockito.mock(HazelcastInstance.class);
		IQueue<Object> mockqueue = Mockito.mock(IQueue.class);
		when(mocknull.getQueue(ArgumentMatchers.any())).thenReturn(mockqueue);
		when(mockqueue.poll()).thenReturn("");
		assertThat(svc.getMsg(mocknull).getFullmsg(), containsString("No Data."));
	}

	@Test
	void testGetQueueHazelcastNullError() {
		HazelcastInstance mockInstanceError = null;
		assertThrows(NullPointerException.class, () -> {
			svc.getMsg(mockInstanceError);
		});
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
	void testPublishHazelcastNullError() {
		HazelcastInstance mockInstanceError = null;
		assertThrows(NullPointerException.class, () -> {
			svc.publishMsg(mockInstanceError);
		});
	}

	@Test
	void testSubscribeHazelcast() {
		svc.subscribeHazelcast(mockInstance, svc.createHazelcastMessageListener());
		assertThat(svc.publishMsg(mockInstance).getFullmsg(), containsString(respbody));
	}
}
