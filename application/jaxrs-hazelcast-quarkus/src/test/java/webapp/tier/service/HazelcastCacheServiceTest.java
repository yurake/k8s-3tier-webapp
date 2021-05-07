package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;

@QuarkusTest
class HazelcastCacheServiceTest {

	@Inject
	HazelcastCacheService svc;

	@ConfigProperty(name = "hazelcast.cache.name")
	String cachename;

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
	void testPutMsgHazelcast() throws NoSuchAlgorithmException {
		assertThat(svc.setMsg(mockInstance).getFullmsg(), containsString(respbody));
	}

	@Test
	void testPutMsgHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getMap(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		try {
			svc.setMsg(mockInstanceError);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e.getClass(), is(IllegalStateException.class));
		}
	}

	@Test
	void testPutMsgHazelcastNullError() {
		HazelcastInstance mockInstanceError = null;
		assertThrows(NullPointerException.class, () -> {
			svc.setMsg(mockInstanceError);
		});
	}

	@Test
	void testGetMsgHazelcast() {
		assertThat(svc.getMsg(mockInstance).getMessage(), is("No Data."));
	}

	@Test
	void testGetMsgHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getMap(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		try {
			svc.getMsg(mockInstanceError);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e.getClass(), is(IllegalStateException.class));
		}
	}

	@Test
	void testGetMsgHazelcastNullError() {
		HazelcastInstance mockInstanceError = null;
		assertThrows(NullPointerException.class, () -> {
			svc.getMsg(mockInstanceError);
		});
	}

	@Test
	void testGetMsgList0() throws NoSuchAlgorithmException, RuntimeException {
		List<MsgBean> msgbeans = svc.getMsgList(mockInstance);
		msgbeans.forEach(s -> {
			assertThat(s.getId(), is(0));
			assertThat(s.getMessage(), is("No Data."));
		});
	}

	@Test
	void testGetMsgList2() throws NoSuchAlgorithmException {
		List<Integer> expecteds = new ArrayList<>();
		Map<Integer, String> map = mockInstance.getMap(cachename);
		for (int i = 0; i < 2; i++) {
			MsgBean msgbean = new MsgBean(CreateId.createid(),respbody , "Set");
			map.put(msgbean.getId(), msgbean.getMessage());
			expecteds.add(msgbean.getId());
		}

		List<MsgBean> msgbeans = svc.getMsgList(mockInstance);
		msgbeans.forEach(s -> {
			assertThat(s.getFullmsg(), containsString(respbody));
			assertThat(expecteds.contains(s.getId()), is(true));
		});
	}
}
