package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastCacheServiceTest{

	@Inject
	HazelcastCacheService svc;

	private static HazelcastInstance mockInstance;

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
	void testGetMapHazelcast() {
		try {
			svc.getMsg(mockInstance);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testIsActiveTrue() {
		assertThat(svc.isActive(), is(true));
	}

	@Test
	void testIsActiveFalse() {
		HazelcastCacheService csvc = new HazelcastCacheService() {
			public HazelcastInstance createHazelcastInstance() {
				return mockInstance;
			}
		};
		after();
		assertThat(csvc.isActive(), is(false));
	}

}
