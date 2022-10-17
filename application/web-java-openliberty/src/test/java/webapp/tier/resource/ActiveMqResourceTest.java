package webapp.tier.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;
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
			Response resp = rsc.actputcache();
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
			Response resp = rsc.actputcache();
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
			Response resp = rsc.actgetcache();
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
			Response resp = rsc.actgetcache();
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
			Response resp = rsc.actpublish();
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
			Response resp = rsc.actpublish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
