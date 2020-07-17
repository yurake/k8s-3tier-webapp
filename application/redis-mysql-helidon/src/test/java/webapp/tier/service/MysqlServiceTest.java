package webapp.tier.service;

import org.junit.jupiter.api.Test;

class MysqlServiceTest {

	@Test
	void test() {
		MysqlService svc = new MysqlService();
		String[] receivedbody = { "1111", "Test" };
		svc.insertMysql(receivedbody);
	}

}
