package webapp.tier.db;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

@SuppressWarnings("deprecation")
class PostgresServiceTest {

	private static SimpleNamingContextBuilder builder;

	@BeforeAll
	public static void setupEach() throws JMSException, IllegalStateException, NamingException, SQLException {
		builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

		ResultSet result = mock(ResultSet.class);
		Statement stsm = mock(Statement.class);
		java.sql.Connection conn = mock(java.sql.Connection.class);
		DataSource ds = mock(DataSource.class);
		when(stsm.executeQuery(anyString())).thenReturn(result);
		when(ds.getConnection()).thenReturn(conn);
		when(conn.createStatement()).thenReturn(stsm);
		builder.bind("jdbc/postgres", ds);

		MessageProducer pro = mock(MessageProducer.class);
		MessageConsumer con = mock(MessageConsumer.class);
		Session session = mock(Session.class);
		javax.jms.Connection jmsconn = mock(javax.jms.Connection.class);
		ConnectionFactory cf = mock(ConnectionFactory.class);
		when(session.createProducer(ArgumentMatchers.any())).thenReturn(pro);
		when(session.createConsumer(ArgumentMatchers.any())).thenReturn(con);
		when(jmsconn.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
		when(cf.createConnection()).thenReturn(jmsconn);
		builder.bind("jms/QueueConnectionFactory", cf);
		Queue q = mock(Queue.class);
		builder.bind("jms/ActiveMQueue", q);
		Topic t = mock(Topic.class);
		builder.bind("jms/ActiveMQTopic", t);
	}

	@Test
	void testgetConnection() throws SQLException, NamingException {
		PostgresService svc = new PostgresService();
		try {
			svc.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testconnectionStatusTrue() {
		PostgresService svc = new PostgresService();
		assertThat(svc.connectionStatus(), is(true));
	}

	@Test
	void testconnectionStatusNamingExceptionFalse() throws IllegalStateException, NamingException, SQLException {
		PostgresService svc = new PostgresService() {
			public Connection getConnection() throws NamingException, SQLException {
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup("test");
				return ds.getConnection();
			}
		};
		assertThat(svc.connectionStatus(), is(false));
	}

	@Test
	void testconnectionStatusSQLExceptionFalse() throws IllegalStateException, NamingException, SQLException {
		PostgresService svc = new PostgresService() {
			public Connection getConnection() throws NamingException, SQLException {
				DataSource ds = mock(DataSource.class);
				when(ds.getConnection()).thenThrow(new SQLException());
				return ds.getConnection();
			}
		};
		assertThat(svc.connectionStatus(), is(false));
	}

	@Test
	void testinsert() throws SQLException, NamingException {
		PostgresService svc = new PostgresService();
		String result = svc.insert();
		assertThat(result, containsString("INSERT INTO msg (id, msg) VALUES ("));
		assertThat(result, containsString(", 'Hello k8s-3tier-webapp!')"));
	}

	@Test
	void testselectWithNoData() throws SQLException, NamingException {
		PostgresService svc = new PostgresService();
		String result = svc.select().get(0);
		assertThat(result, is("No Data"));
	}

	@Test
	void testdelete() throws SQLException, NamingException {
		PostgresService svc = new PostgresService();
		String result = svc.delete();
		assertThat(result, is("Deleted"));
	}
}
