package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PostgresServiceTest {

	@Test
	void testConnectionStatusError() {
		PostgresService svc = new PostgresService();
		assertThat(svc.connectionStatus(), is(false));
	}

	@Test
	void testInsertMysqlError() {
		PostgresService svc = new PostgresService();
		try {
			svc.insertMsg();
			fail();
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			assertEquals("Insert Error.", e.getMessage());
		}
	}

	@Test
	void testSelectMsgError() {
		PostgresService svc = new PostgresService();
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
		PostgresService svc = new PostgresService();
		try {
			svc.deleteMsg();
			fail();
		} catch (SQLException e) {
			e.printStackTrace();
			assertEquals("Delete Error.", e.getMessage());
		}
	}
}
