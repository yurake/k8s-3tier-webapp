package webapp.tier.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class HazelcastServiceStatusTest {

	@Inject
	HazelcastServiceStatus svc;

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
	void testIsActiveTrue() {
		assertThat(svc.isActive(), is(true));
	}

	@Test
	void testIsActiveFalse() {
		HazelcastServiceStatus csvc = new HazelcastServiceStatus() {
			public HazelcastInstance createHazelcastInstance() {
				return mockInstance;
			}
		};
		after();
		assertThat(csvc.isActive(), is(false));
	}

	@Test
	void testIsActiveFalseInstanceNull() {
		HazelcastServiceStatus csvc = new HazelcastServiceStatus() {
			public HazelcastInstance createHazelcastInstance() {
				return null;
			}
		};
		after();
		assertThat(csvc.isActive(), is(false));
	}

}
