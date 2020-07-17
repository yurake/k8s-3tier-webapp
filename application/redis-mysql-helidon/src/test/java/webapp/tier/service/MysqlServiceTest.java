package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class MysqlServiceTest {

	@Test
	void test() {
		MysqlService svc = new MysqlService();
		String[] receivedbody = { "1111", "Test" };
		try {
			svc.insertMysql(receivedbody);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
