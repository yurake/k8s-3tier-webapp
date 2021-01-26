package webapp.tier.resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import webapp.tier.cache.HazelcastCacheService;
import webapp.tier.mq.HazelcastMqService;

public class HazelcastResourceTest {

	private static HazelcastInstance mockInstance;

	@BeforeEach
	public void setup() throws IOException {
		mockInstance = Hazelcast.newHazelcastInstance();
	}

	@AfterEach
	public void after() {
		mockInstance.shutdown();
	}

	private HazelcastResource createHazelcastCacheServiceNull() throws Exception {
		return new HazelcastResource() {
			HazelcastCacheService createHazelcastCacheService() {
				return null;
			}
		};
	}

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

	@Test
	public void testputcache() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			Response resp = rsc.hazputcache();
			assertThat(resp.getStatus(), is(200));
			String result = resp.getEntity().toString();
			assertThat(result, containsString("Set id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testputcacheError() {
		try {
			HazelcastResource rsc = createHazelcastCacheServiceNull();
			Response resp = rsc.hazputcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcache() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			Response resp = rsc.hazgetcache();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("[No Data]"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetcacheError() {
		try {
			HazelcastResource rsc = createHazelcastCacheServiceNull();
			Response resp = rsc.hazgetcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void testputqueue() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			Response resp = rsc.hazputqueue();
			assertThat(resp.getStatus(), is(200));
			String result = resp.getEntity().toString();
			assertThat(result, containsString("Set id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testputqueueError() {
		try {
			HazelcastResource rsc = createHazelcastMqServiceNull();
			Response resp = rsc.hazputqueue();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetqueue() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			Response resp = rsc.hazgetqueue();
			assertThat(resp.getStatus(), is(200));
			assertThat(resp.getEntity().toString(), is("No Data"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetqueueError() {
		try {
			HazelcastResource rsc = createHazelcastMqServiceNull();
			Response resp = rsc.hazgetqueue();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublish() {
		try {
			HazelcastResource rsc = new HazelcastResource();
			Response resp = rsc.hazpublish();
			assertThat(resp.getStatus(), is(200));
			String result = resp.getEntity().toString();
			assertThat(result, containsString("Publish id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp via hazelcast-subscriber!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublishError() {
		try {
			HazelcastResource rsc = createHazelcastMqServiceNull();
			Response resp = rsc.hazpublish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
