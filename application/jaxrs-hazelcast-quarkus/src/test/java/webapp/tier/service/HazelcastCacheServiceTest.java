package webapp.tier.service;

import static org.hamcrest.CoreMatchers.containsString;
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
class HazelcastCacheServiceTest{

	@Inject
	private HazelcastCacheService svc;

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
	void testPutMapHazelcast() {
		try {
			svc.setMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testPutMapHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getMap(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.setMsg(mockInstanceError).getFullmsg(), containsString(respbody));
	}

	@Test
	void testGetMapHazelcast() {
		try {
			svc.getMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testGetMapHazelcastError() {
		HazelcastInstance mockInstanceError = Mockito.mock(HazelcastInstance.class);
		when(mockInstanceError.getMap(ArgumentMatchers.any())).thenThrow(new IllegalStateException());
		assertThat(svc.getMsg(mockInstanceError).getFullmsg(), containsString("No Data."));
	}

}
