package webapp.tier.mq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import webapp.tier.util.BeforeAllTest;

class ActiveMqServiceTest {

	@BeforeAll
	public static void setupEach() {
		BeforeAllTest.getInstance();
	}

	@Test
	void testgetConnection() {
		try {
			ActiveMqService svc = new ActiveMqService();
			svc.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testcloseAllNull() {
		try {
			ActiveMqService svc = new ActiveMqService();
			svc.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testputActiveMq() {
		try {
			ActiveMqService svc = new ActiveMqService();
			String result = svc.putActiveMq();
			assertThat(result, is(notNullValue()));
			assertThat(result, containsString("Set"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testgetActiveMqWithNoData() {
		try {
			ActiveMqService svc = new ActiveMqService();
			String result = svc.getActiveMq();
			assertThat(result, is(notNullValue()));
			assertThat(result, is("No Data"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testpublishActiveMq() {
		try {
			ActiveMqService svc = new ActiveMqService();
			String result = svc.publishActiveMq();
			assertThat(result, is(notNullValue()));
			assertThat(result, containsString("Publish"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
