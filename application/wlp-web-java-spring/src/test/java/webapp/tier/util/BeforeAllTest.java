package webapp.tier.util;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.sql.DataSource;

import org.mockito.ArgumentMatchers;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

@SuppressWarnings("deprecation")
public class BeforeAllTest {

	private static SimpleNamingContextBuilder builder;

	private BeforeAllTest() {
	}

	public static void getInstance() {
		try {
			builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();

			ResultSet result = mock(ResultSet.class);
			Statement stsm = mock(Statement.class);
			java.sql.Connection conn = mock(java.sql.Connection.class);
			DataSource ds = mock(DataSource.class);
			when(stsm.executeQuery(anyString())).thenReturn(result);
			when(ds.getConnection()).thenReturn(conn);
			when(conn.createStatement()).thenReturn(stsm);
			builder.bind("jdbc/postgres", ds);
			builder.bind("jdbc/mysql", ds);

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
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
