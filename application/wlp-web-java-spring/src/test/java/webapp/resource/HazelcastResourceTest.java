package webapp.resource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import webapp.tier.cache.HazelcastCacheService;
import webapp.tier.mq.HazelcastMqService;

public class HazelcastResourceTest {

	/**
	private HazelcastResource createHazelcastCacheService() throws Exception {
		HazelcastCacheService casvc = mock(HazelcastCacheService.class);
		List<String> allmsg = new ArrayList<>();
		allmsg.add("OK");
		when(casvc.putMapHazelcast(ArgumentMatchers.any())).thenReturn("OK");
		when(casvc.getMapHazelcast(ArgumentMatchers.any())).thenReturn(allmsg);
		return new HazelcastResource() {
			HazelcastCacheService createHazelcastCacheService() {
				return casvc;
			}
		};
	}
	**/

	private HazelcastResource createHazelcastCacheServiceNull() throws Exception {
		return new HazelcastResource() {
			HazelcastCacheService createHazelcastCacheService() {
				return null;
			}
		};
	}

	/**
	private HazelcastResource createHazelcastMqService() throws Exception {
		HazelcastMqService mqsvc = mock(HazelcastMqService.class);
		when(mqsvc.putQueueHazelcast(ArgumentMatchers.any())).thenReturn("OK");
		when(mqsvc.getQueueHazelcast(ArgumentMatchers.any())).thenReturn("OK");
		when(mqsvc.publishHazelcast(ArgumentMatchers.any())).thenReturn("OK");
		return new HazelcastResource() {
			HazelcastMqService createHazelcastMqService() {
				return mqsvc;
			}
		};
	}
	**/

	private HazelcastResource createHazelcastMqServiceNull() throws Exception {
		return new HazelcastResource() {
			HazelcastMqService createHazelcastMqService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateHazelcastCacheService() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			assertThat(rsc.createHazelcastCacheService(), is(instanceOf(HazelcastCacheService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testcreateHazelcastMqService() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			assertThat(rsc.createHazelcastMqService(), is(instanceOf(HazelcastMqService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	@Test
	public void testputcache() {
		try {
			HazelcastResource rsc = createHazelcastCacheService();
			Response resp = rsc.putcache();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	**/

	@Test
	public void testPutcacheError() {
		try {
			HazelcastResource rsc = createHazelcastCacheServiceNull();
			Response resp = rsc.putcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	@Test
	public void testgetcache() {
		try {
			HazelcastResource rsc = createHazelcastCacheService();
			Response resp = rsc.getcache();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	**/

	@Test
	public void testgetcacheError() {
		try {
			HazelcastResource rsc = createHazelcastCacheServiceNull();
			Response resp = rsc.getcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	/**
	@Test
	public void testputqueue() {
		try {
			HazelcastResource rsc = createHazelcastMqService();
			Response resp = rsc.putqueue();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	**/

	@Test
	public void testputqueueError() {
		try {
			HazelcastResource rsc = createHazelcastMqServiceNull();
			Response resp = rsc.putqueue();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	@Test
	public void testgetqueue() {
		try {
			HazelcastResource rsc = createHazelcastMqService();
			Response resp = rsc.getqueue();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	**/

	@Test
	public void testgetqueueError() {
		try {
			HazelcastResource rsc = createHazelcastMqServiceNull();
			Response resp = rsc.getqueue();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	@Test
	public void testpublish() {
		try {
			HazelcastResource rsc = createHazelcastMqService();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("OK"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	**/

	@Test
	public void testpublishError() {
		try {
			HazelcastResource rsc = createHazelcastMqServiceNull();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
