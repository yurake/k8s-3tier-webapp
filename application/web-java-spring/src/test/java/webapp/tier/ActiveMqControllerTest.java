package webapp.tier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.mq.ActiveMqService;

public class ActiveMqControllerTest {

	ActiveMqController createActiveMqService() throws Exception {
		ActiveMqService svc = mock(ActiveMqService.class);
		when(svc.putActiveMq()).thenReturn("OK");
		when(svc.getActiveMq()).thenReturn("OK");
		when(svc.publishActiveMq()).thenReturn("OK");
		return new ActiveMqController() {
			ActiveMqService createActiveMqService() {
				return svc;
			}
		};
	}

	ActiveMqController createActiveMqServiceNull() throws Exception {
		return new ActiveMqController() {
			ActiveMqService createActiveMqService() {
				return null;
			}
		};
	}

//	@Test
//	public void testcreateActiveMqService() {
//		try {
//			ActiveMqController rsc = new ActiveMqController();
//			assertThat(rsc.createActiveMqService(), is(instanceOf(ActiveMqService.class)));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testPutcache() {
//		try {
//			ActiveMqController rsc = createActiveMqService();
//			Response resp = rsc.putcache();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("OK"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testPutcacheError() {
//		try {
//			ActiveMqController rsc = createActiveMqServiceNull();
//			Response resp = rsc.putcache();
//			assertThat(resp.getStatus(), is(500));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testget() {
//		try {
//			ActiveMqController rsc = createActiveMqService();
//			Response resp = rsc.get();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("OK"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

	@Test
	public void testgetError() {
		try {
			ActiveMqController rsc = createActiveMqServiceNull();
			Response resp = rsc.get();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

//	@Test
//	public void testpublish() {
//		try {
//			ActiveMqController rsc = createActiveMqService();
//			Response resp = rsc.publish();
//			assertThat(resp.getStatus(), is(200));
//			assertThat(resp.getEntity().toString(), is("OK"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}

//	@Test
//	public void testpublishError() {
//		try {
//			ActiveMqController rsc = createActiveMqServiceNull();
//			Response resp = rsc.publish();
//			assertThat(resp.getStatus(), is(500));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
}
