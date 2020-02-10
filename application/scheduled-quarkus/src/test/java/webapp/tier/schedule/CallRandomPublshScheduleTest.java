package webapp.tier.schedule;

import org.junit.jupiter.api.Test;

class CallRandomPublshScheduleTest {

	@Test
	void testCallRandomPublshError() {
		CallRandomPublshSchedule sch = new CallRandomPublshSchedule();
		sch.callRandomPublsh();
	}

	@Test
	void testCallDeleteDbsError() {
		CallRandomPublshSchedule sch = new CallRandomPublshSchedule();
		sch.callDeleteDbs();
	}

}
