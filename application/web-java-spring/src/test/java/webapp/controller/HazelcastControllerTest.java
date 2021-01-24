package webapp.controller;

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

public class HazelcastControllerTest {

	private static HazelcastInstance mockInstance;

	@BeforeEach
	public void setup() throws IOException {
		mockInstance = Hazelcast.newHazelcastInstance();
	}

	@AfterEach
	public void after() {
		mockInstance.shutdown();
	}

	private HazelcastController createHazelcastCacheServiceNull() throws Exception {
		return new HazelcastController() {
			HazelcastCacheService createHazelcastCacheService() {
				return null;
			}
		};
	}

	private HazelcastController createHazelcastMqServiceNull() throws Exception {
		return new HazelcastController() {
			HazelcastMqService createHazelcastMqService() {
				return null;
			}
		};
	}

	@Test
	public void testcreateHazelcastCacheService() {
		try {
			HazelcastController rsc = new HazelcastController();
			assertThat(rsc.createHazelcastCacheService(), is(instanceOf(HazelcastCacheService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testcreateHazelcastMqService() {
		try {
			HazelcastController rsc = new HazelcastController();
			assertThat(rsc.createHazelcastMqService(), is(instanceOf(HazelcastMqService.class)));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testputcache() {
		try {
			HazelcastController rsc = new HazelcastController();
			Response resp = rsc.putcache();
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
			HazelcastController rsc = createHazelcastCacheServiceNull();
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
			HazelcastController rsc = new HazelcastController();
			Response resp = rsc.getcache();
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
			HazelcastController rsc = createHazelcastCacheServiceNull();
			Response resp = rsc.getcache();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void testputqueue() {
		try {
			HazelcastController rsc = new HazelcastController();
			Response resp = rsc.putqueue();
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
			HazelcastController rsc = createHazelcastMqServiceNull();
			Response resp = rsc.putqueue();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testgetqueue() {
		try {
			HazelcastController rsc = new HazelcastController();
			Response resp = rsc.getqueue();
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
			HazelcastController rsc = createHazelcastMqServiceNull();
			Response resp = rsc.getqueue();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testpublish() {
		try {
			HazelcastController rsc = new HazelcastController();
			Response resp = rsc.publish();
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
			HazelcastController rsc = createHazelcastMqServiceNull();
			Response resp = rsc.publish();
			assertThat(resp.getStatus(), is(500));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
