package webapp.tier.mq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

@SuppressWarnings("deprecation")
class ActiveMqServiceTest {

	private SimpleNamingContextBuilder builder;

	@BeforeEach
	public void setupEach() throws JMSException, IllegalStateException, NamingException {
		MessageProducer pro = mock(MessageProducer.class);
		MessageConsumer con = mock(MessageConsumer.class);
		Session session = mock(Session.class);
		Connection conn = mock(Connection.class);
		ConnectionFactory cf = mock(ConnectionFactory.class);
		when(session.createProducer(ArgumentMatchers.any())).thenReturn(pro);
		when(session.createConsumer(ArgumentMatchers.any())).thenReturn(con);
		when(conn.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
		when(cf.createConnection()).thenReturn(conn);
		builder = new SimpleNamingContextBuilder();
		builder.bind("jms/QueueConnectionFactory", cf);
		Queue q = mock(Queue.class);
		builder.bind("jms/ActiveMQueue", q);
		Topic t = mock(Topic.class);
		builder.bind("jms/ActiveMQTopic", t);
		builder.activate();
	}


	@AfterEach
	public void teardown() {
		builder.deactivate();
	}

	@Test
	void testgetConnection() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		svc.getConnection();
	}

	@Test
	void testcloseAllNull() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		try {
			svc.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testputActiveMq() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		String result = svc.putActiveMq();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsString("Set"));
	}

	@Test
	void testgetActiveMqWithNoData() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		String result = svc.getActiveMq();
		assertThat(result, is(notNullValue()));
		assertThat(result, is("No Data"));
	}

	@Test
	void testpublishActiveMq() throws Exception {
		ActiveMqService svc = new ActiveMqService();
		String result = svc.publishActiveMq();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsString("Publish"));
	}
}
