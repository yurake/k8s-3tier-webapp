package webapp.tier;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.RabbitmqController;
import webapp.tier.mq.RabbitmqService;

public class RabbitmqControllerTest {

	private RabbitmqController createRabbitmqService() throws Exception {
		RabbitmqService svc = mock(RabbitmqService.class);
		when(svc.put()).thenReturn("OK");
		when(svc.get()).thenReturn("OK");
		when(svc.publish()).thenReturn("OK");
		return new RabbitmqController() {
			RabbitmqService createRabbitmqService() {
				return svc;
			}
		};
	}

	private RabbitmqController createRabbitmqServiceNull() throws Exception {
		return new RabbitmqController() {
			RabbitmqService createRabbitmqService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateRabbitmqService() {
		try {
			RabbitmqController rsc = new RabbitmqController();
			assertThat(rsc.createRabbitmqService(), is(instanceOf(RabbitmqService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutcache() {
		try {
			RabbitmqController rsc = createRabbitmqService();
			Response resp = rsc.put();
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
			RabbitmqController rsc = createRabbitmqServiceNull();
			Response resp = rsc.put();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcache() {
		try {
			RabbitmqController rsc = createRabbitmqService();
			Response resp = rsc.get();
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
			RabbitmqController rsc = createRabbitmqServiceNull();
			Response resp = rsc.get();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublish() {
		try {
			RabbitmqController rsc = createRabbitmqService();
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
			RabbitmqController rsc = createRabbitmqServiceNull();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
