package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MysqlServiceTest {

	@Test
	void testConnectionStatusError() {
		MysqlService svc = new MysqlService();
		assertThat(svc.connectionStatus(), is(false));
	}

	@Test
	void testInsertMysqlError() {
		MysqlService svc = new MysqlService();
		try {
			svc.insertMysql();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals(e.getMessage(), "Insert Error.");
		}
	}

	@Test
	void testInsertMsgError() {
		MysqlService svc = new MysqlService();
		String[] test = { "11111", "test" };
		try {
			svc.insertMsg(test);
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals(e.getMessage(), "Insert Error.");
		}
	}

	@Test
	void testSelectMsg() {
		MysqlService svc = new MysqlService();
		try {
			svc.selectMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals(e.getMessage(), "Select Error.");
		}
	}

	@Test
	void testDeleteMsg() {
		MysqlService svc = new MysqlService();
		try {
			svc.deleteMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals(e.getMessage(), "Delete Error.");
		}
	}
}
