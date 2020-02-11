package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MemcachedServiceTest {

	@Test
	void testSetMemcachedError() {
		MemcachedService svc = new MemcachedService();

		try {
			svc.setMemcached();
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is("Failed set to Memcached"));
		}
	}

	@Test
	void testGetMemcachedError() {
		MemcachedService svc = new MemcachedService();
		try {
			svc.getMemcached();
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is("Failed get from Memcached"));
		}
	}

}
