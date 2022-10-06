package webapp.tier.schedule;

import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class CallRandomPublshScheduleTest {

	@Inject
	private CallRandomPublshSchedule rps;

	@Test
	void testCallRandomPublsh() {
		try {
			rps.callRandomPublsh();
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

	@Test
	void testCallDeleteDbsError() {
		try {
			rps.callDeleteDbs();
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}

}
