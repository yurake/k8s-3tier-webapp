package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class MemcachedResourceTest {

	@Test
	public void testSet() {
		MemcachedResource rsc = new MemcachedResource();
		Response resp = rsc.set();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testGet() {
		MemcachedResource rsc = new MemcachedResource();
		Response resp = rsc.get();
		assertThat(resp.getStatus(), is(500));
	}

}
