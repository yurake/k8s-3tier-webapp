package webapp.tier.service;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MemcachedServiceTest {

	@Test
	void testSetMemcachedError() {
		MemcachedService svc = new MemcachedService();
		String result = svc.setMemcached();
		assertThat(result, is("Failed set to Memcached"));
	}

	@Test
	void testGetMemcachedError() {
		MemcachedService svc = new MemcachedService();
		String result = svc.getMemcached();
		assertThat(result, is("Failed get from Memcached"));
	}

}
