package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
class PostgresServiceTest {

	@Inject
	PostgresService svc;

	String respbody = "message: Hello k8s-3tier-webapp with quarkus";

	@BeforeEach
	public void createTable() {
		String createsql = "CREATE TABLE msg (id SERIAL PRIMARY KEY, msg TEXT NOT NULL)";
		try (Connection con = DriverManager
				.getConnection("jdbc:h2:tcp://localhost/mem:webapp;DB_CLOSE_DELAY=-1");
				Statement stmt = con.createStatement()) {
			stmt.executeUpdate(createsql);
		} catch (SQLException e) {
			e.fillInStackTrace();
		}
	}

	@AfterEach
	public void dropTable() {
		String createsql = "DROP TABLE msg";
		try (Connection con = DriverManager
				.getConnection("jdbc:h2:tcp://localhost/mem:webapp;DB_CLOSE_DELAY=-1");
				Statement stmt = con.createStatement()) {
			stmt.executeUpdate(createsql);
		} catch (SQLException e) {
			e.fillInStackTrace();
		}
	}

	@Test
	void testConnectionStatusTrue() throws Exception {
		assertThat(svc.connectionStatus(), is(true));
	}

	@Test
	void testInsertMysql() {
		try {
			MsgBean bean = svc.insertMsg();
			assertThat(bean.getFullmsg(), containsString(respbody));
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testInsertMysqlError() {
		try {
			dropTable();
			svc.insertMsg();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			assertThat(e.getMessage(), is("Insert Error."));
		}
	}

	@Test
	void testSelectMsgList10() {
		try {
			List<Integer> expecteds = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				expecteds.add(svc.insertMsg().getId());
			}
			List<MsgBean> msgbeans = svc.selectMsg();
			msgbeans.forEach(s -> {
				assertThat(s.getFullmsg(), containsString(respbody));
				assertThat(expecteds.contains(s.getId()), is(true));
			});
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSelectMsgNoData() {
		try {
			List<MsgBean> msgbeans = svc.selectMsg();
			msgbeans.forEach(s -> {
				assertThat(s.getMessage(), is("No Data."));
				assertThat(s.getId(), is(0));
			});
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSelectMsgError() {
		try {
			dropTable();
			svc.selectMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Select Error.", e.getMessage());
		}
	}

	@Test
	void testDeleteMsgNoData() {
		try {
			String recv = svc.deleteMsg();
			assertThat(recv, is("Delete Msg Records"));
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testDeleteMsgWithData() {
		try {
			List<Integer> expecteds = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				expecteds.add(svc.insertMsg().getId());
			}
			String recv = svc.deleteMsg();
			assertThat(recv, is("Delete Msg Records"));
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testDeleteMsgError() {
		try {
			dropTable();
			svc.deleteMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Delete Error.", e.getMessage());
		}
	}
}
