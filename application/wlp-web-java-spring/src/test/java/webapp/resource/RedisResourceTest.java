package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class RedisResourceTest {

	@Test
	public void testSetError() {
		RedisResource rsc = new RedisResource();
		Response resp = rsc.set();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testGetError() {
		RedisResource rsc = new RedisResource();
		Response resp = rsc.get();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testPublishError() {
		RedisResource rsc = new RedisResource();
		Response resp = rsc.publish();
		assertThat(resp.getStatus(), is(500));
	}

}
