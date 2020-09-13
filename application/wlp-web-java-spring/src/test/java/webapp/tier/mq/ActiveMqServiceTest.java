package webapp.tier.mq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import webapp.tier.util.BeforeAllTest;

class ActiveMqServiceTest {

	@BeforeAll
	public static void setupEach() throws NamingException, SQLException, JMSException {
		BeforeAllTest.getInstance();
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
