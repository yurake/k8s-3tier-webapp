package webapp.resource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.cache.RedisService;

public class RedisResourceTest {

	private RedisResource createRedisService() throws Exception {
		RedisService svc = mock(RedisService.class);
		List<String> allmsg = new ArrayList<>();
		allmsg.add("OK");
		when(svc.set()).thenReturn("OK");
		when(svc.get()).thenReturn(allmsg);
		when(svc.publish()).thenReturn("OK");
		return new RedisResource() {
			RedisService createRedisService() {
				return svc;
			}
		};
	}

	private RedisResource createRedisServiceNull() throws Exception {
		return new RedisResource() {
			RedisService createRedisService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateRedisService() {
		try {
			RedisResource rsc = new RedisResource();
			assertThat(rsc.createRedisService(), is(instanceOf(RedisService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testsetcache() {
		try {
			RedisResource rsc = createRedisService();
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
			RedisResource rsc = createRedisServiceNull();
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
			RedisResource rsc = createRedisService();
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
			RedisResource rsc = createRedisServiceNull();
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
			RedisResource rsc = createRedisService();
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
			RedisResource rsc = createRedisServiceNull();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
