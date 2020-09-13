package webapp.tier.db;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import webapp.tier.util.BeforeAllTest;

class MysqlServiceTest {

	@BeforeAll
	public static void setupAll() {
		BeforeAllTest.getInstance();
	}

	@Test
	void testgetConnection() {
		MysqlService svc = new MysqlService();
		try {
			svc.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testconnectionStatusTrue() {
		MysqlService svc = new MysqlService();
		assertThat(svc.connectionStatus(), is(true));
	}

	@Test
	void testconnectionStatusNamingExceptionFalse() {
		MysqlService svc = new MysqlService() {
			public Connection getConnection() throws NamingException, SQLException {
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup("test");
				return ds.getConnection();
			}
		};
		assertThat(svc.connectionStatus(), is(false));
	}

	@Test
	void testconnectionStatusSQLExceptionFalse() {
		MysqlService svc = new MysqlService() {
			public Connection getConnection() throws NamingException, SQLException {
				DataSource ds = mock(DataSource.class);
				when(ds.getConnection()).thenThrow(new SQLException());
				return ds.getConnection();
			}
		};
		assertThat(svc.connectionStatus(), is(false));
	}

	@Test
	void testinsert() {
		try {
			MysqlService svc = new MysqlService();
			String result = svc.insert();
			assertThat(result, containsString("INSERT INTO msg (id, msg) VALUES ("));
			assertThat(result, containsString(", 'Hello k8s-3tier-webapp!')"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testselectWithNoData() {
		try {
			MysqlService svc = new MysqlService();
			String result = svc.select().get(0);
			assertThat(result, is("No Data"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testdelete() {
		try {
			MysqlService svc = new MysqlService();
			String result = svc.delete();
			assertThat(result, is("Deleted"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
