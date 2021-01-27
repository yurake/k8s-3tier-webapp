package webapp.tier.mq;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ActiveMqServiceTest {

	@Test
	void testgetConnectionNullPointerException() {
		try {
			ActiveMqService svc = new ActiveMqService();
			svc.getConnection();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
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

//	@Test
//	void testputActiveMq() {
//		try {
//			ActiveMqService svc = new ActiveMqService();
//			String result = svc.putActiveMq();
//			assertThat(result, is(notNullValue()));
//			assertThat(result, containsString("Set"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	void testgetActiveMqWithNoData() {
//		try {
//			ActiveMqService svc = new ActiveMqService(){
//				public Session getConnection() throws Exception{
//					MessageConsumer consumer = mock(MessageConsumer.class);
//					Session session = mock(Session.class);
//					Connection conn = mock(Connection.class);
//					ConnectionFactory cf = mock(ConnectionFactory.class);
//					when(cf.createConnection()).thenReturn(conn);
//					when(conn.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
//					when(session.createConsumer(ArgumentMatchers.any())).thenReturn(consumer);
//					return session;
//				}
//			};
//			String result = svc.getActiveMq();
//			assertThat(result, is(notNullValue()));
//			assertThat(result, is("No Data"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	void testpublishActiveMq() {
//		try {
//			ActiveMqService svc = new ActiveMqService();
//			String result = svc.publishActiveMq();
//			assertThat(result, is(notNullValue()));
//			assertThat(result, containsString("Publish"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
}
