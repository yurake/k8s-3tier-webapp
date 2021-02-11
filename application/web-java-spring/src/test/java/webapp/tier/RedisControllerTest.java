package webapp.tier;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.RedisController;
import webapp.tier.cache.RedisService;

public class RedisControllerTest {

	private RedisController createRedisService() throws Exception {
		RedisService svc = mock(RedisService.class);
		List<String> allmsg = new ArrayList<>();
		allmsg.add("OK");
		when(svc.set()).thenReturn("OK");
		when(svc.get()).thenReturn(allmsg);
		when(svc.publish()).thenReturn("OK");
		return new RedisController() {
			RedisService createRedisService() {
				return svc;
			}
		};
	}

	private RedisController createRedisServiceNull() throws Exception {
		return new RedisController() {
			RedisService createRedisService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateRedisService() {
		try {
			RedisController rsc = new RedisController();
			assertThat(rsc.createRedisService(), is(instanceOf(RedisService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testsetcache() {
		try {
			RedisController rsc = createRedisService();
			Response resp = rsc.set();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testsetError() {
		try {
			RedisController rsc = createRedisServiceNull();
			Response resp = rsc.set();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcache() {
		try {
			RedisController rsc = createRedisService();
			Response resp = rsc.get();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("[OK]"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcacheError() {
		try {
			RedisController rsc = createRedisServiceNull();
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
			RedisController rsc = createRedisService();
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
			RedisController rsc = createRedisServiceNull();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
