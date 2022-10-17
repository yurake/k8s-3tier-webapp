package webapp.tier.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;
import webapp.tier.mq.RabbitmqService;

public class RabbitmqResourceTest {

	private RabbitmqResource createRabbitmqService() throws Exception {
		RabbitmqService svc = mock(RabbitmqService.class);
		when(svc.put()).thenReturn("OK");
		when(svc.get()).thenReturn("OK");
		when(svc.publish()).thenReturn("OK");
		return new RabbitmqResource() {
			RabbitmqService createRabbitmqService() {
				return svc;
			}
		};
	}

	private RabbitmqResource createRabbitmqServiceNull() throws Exception {
		return new RabbitmqResource() {
			RabbitmqService createRabbitmqService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateRabbitmqService() {
		try {
			RabbitmqResource rsc = new RabbitmqResource();
			assertThat(rsc.createRabbitmqService(), is(instanceOf(RabbitmqService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutcache() {
		try {
			RabbitmqResource rsc = createRabbitmqService();
			Response resp = rsc.rabput();
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
			RabbitmqResource rsc = createRabbitmqServiceNull();
			Response resp = rsc.rabput();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcache() {
		try {
			RabbitmqResource rsc = createRabbitmqService();
			Response resp = rsc.rabget();
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
			RabbitmqResource rsc = createRabbitmqServiceNull();
			Response resp = rsc.rabget();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublish() {
		try {
			RabbitmqResource rsc = createRabbitmqService();
			Response resp = rsc.rabpublish();
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
			RabbitmqResource rsc = createRabbitmqServiceNull();
			Response resp = rsc.rabpublish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
