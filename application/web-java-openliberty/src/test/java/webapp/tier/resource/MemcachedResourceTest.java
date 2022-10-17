package webapp.tier.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;
import webapp.tier.cache.MemcachedService;

public class MemcachedResourceTest {

	MemcachedResource createMemcachedService() throws Exception {
		MemcachedService svc = mock(MemcachedService.class);
		when(svc.set()).thenReturn("OK");
		when(svc.get()).thenReturn("OK");
		return new MemcachedResource() {
			MemcachedService createMemcachedService() {
				return svc;
			}
		};
	}

	MemcachedResource createMemcachedServiceNull() throws Exception {
		return new MemcachedResource() {
			MemcachedService createMemcachedService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateMemcachedService() {
		try {
			MemcachedResource rsc = new MemcachedResource();
			assertThat(rsc.createMemcachedService(), is(instanceOf(MemcachedService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutcache() {
		try {
			MemcachedResource rsc = createMemcachedService();
			Response resp = rsc.memset();
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
			MemcachedResource rsc = createMemcachedServiceNull();
			Response resp = rsc.memset();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcache() {
		try {
			MemcachedResource rsc = createMemcachedService();
			Response resp = rsc.memget();
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
			MemcachedResource rsc = createMemcachedServiceNull();
			Response resp = rsc.memget();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
