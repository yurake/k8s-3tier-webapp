package webapp.tier.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CallRandomPublshScheduleTest {

	@Test
	void testCallRandomPublshError() {
		CallRandomPublshSchedule sch = new CallRandomPublshSchedule();
		try {
			sch.callRandomPublsh();
			fail();
		} catch (NullPointerException expected) {
			expected.printStackTrace();
		}
	}

	@Test
	void testCallDeleteDbsError() {
		CallRandomPublshSchedule sch = new CallRandomPublshSchedule();
		try {
			sch.callDeleteDbs();
			fail();
		} catch (NullPointerException expected) {
			expected.printStackTrace();
		}
	}

}
