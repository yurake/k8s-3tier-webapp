package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

class MysqlInsertServiceTest {

	@Test
	void testInsertMysqlError() {
		MysqlInsertService svc = new MysqlInsertService();
		String[] receivedbody = { "1111", "Test" };
		try {
			svc.insert(receivedbody);
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testInsertMysql() {
		String[] receivedbody = { "1111", "Test" };
		try {

			Statement stmt = mock(Statement.class);
			Connection conn = mock(Connection.class);
			when(conn.createStatement()).thenReturn(stmt);

			MysqlInsertService svc = new MysqlInsertService() {
				protected Connection getConnection() throws SQLException {
					return conn;
				}
			};

			svc.insert(receivedbody);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
