package webapp.resource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.mq.ActiveMqService;

public class ActiveMqResourceTest {

	private ActiveMqResource createActiveMqService() throws Exception {
		ActiveMqService svc = mock(ActiveMqService.class);
		when(svc.putActiveMq()).thenReturn("OK");
		when(svc.getActiveMq()).thenReturn("OK");
		when(svc.publishActiveMq()).thenReturn("OK");
		return new ActiveMqResource() {
			ActiveMqService createActiveMqService() {
				return svc;
			}
		};
	}

	private ActiveMqResource createActiveMqServiceNull() throws Exception {
		return new ActiveMqResource() {
			ActiveMqService createActiveMqService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateActiveMqService() {
		try {
			ActiveMqResource rsc = new ActiveMqResource();
			assertThat(rsc.createActiveMqService(), is(instanceOf(ActiveMqService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutcache() {
		try {
			ActiveMqResource rsc = createActiveMqService();
			Response resp = rsc.putcache();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutcacheError() {
		try {
			ActiveMqResource rsc = createActiveMqServiceNull();
			Response resp = rsc.putcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcache() {
		try {
			ActiveMqResource rsc = createActiveMqService();
			Response resp = rsc.getcache();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcacheError() {
		try {
			ActiveMqResource rsc = createActiveMqServiceNull();
			Response resp = rsc.getcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublish() {
		try {
			ActiveMqResource rsc = createActiveMqService();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublishError() {
		try {
			ActiveMqResource rsc = createActiveMqServiceNull();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
