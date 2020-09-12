package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class HazelcastResourceErrorTest {

	@Test
	public void testPutcacheError() {
		HazelcastResource rsc = new HazelcastResource();
		Response resp = rsc.putcache();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testGetcacheError() {
		HazelcastResource rsc = new HazelcastResource();
		Response resp = rsc.getcache();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testPutqueueError() {
		HazelcastResource rsc = new HazelcastResource();
		Response resp = rsc.putqueue();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testGetqueueError() {
		HazelcastResource rsc = new HazelcastResource();
		Response resp = rsc.getqueue();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testPublishError() {
		HazelcastResource rsc = new HazelcastResource();
		Response resp = rsc.publish();
		assertThat(resp.getStatus(), is(500));
	}

}
