package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MongodbServiceTest {

	@Test
	void testInsertMysqlError() {
		MongodbService svc = new MongodbService();
		try {
			svc.insertMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Insert Error.", e.getMessage());
		}
	}

	@Test
	void testSelectMsgError() {
		MongodbService svc = new MongodbService();
		try {
			svc.selectMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Select Error.", e.getMessage());
		}
	}

	@Test
	void testDeleteMsgError() {
		MongodbService svc = new MongodbService();
		try {
			svc.deleteMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Delete Error.", e.getMessage());
		}
	}

}
