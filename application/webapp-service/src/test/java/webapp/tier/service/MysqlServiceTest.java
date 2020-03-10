package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

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
			svc.insertMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Insert Error.", e.getMessage());
		}
	}

	@Test
	void testInsertMsgError() {
		MysqlService svc = new MysqlService();
		MsgBean msgbean = new MsgBean(1111, "Test");
		try {
			svc.insertMsg(msgbean);
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Insert Error.", e.getMessage());
		}
	}

	@Test
	void testSelectMsgError() {
		MysqlService svc = new MysqlService();
		try {
			svc.selectMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Select Error.", e.getMessage());
		}
	}

	@Test
	void testDeleteMsgError() {
		MysqlService svc = new MysqlService();
		try {
			svc.deleteMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Delete Error.", e.getMessage());
		}
	}
}
