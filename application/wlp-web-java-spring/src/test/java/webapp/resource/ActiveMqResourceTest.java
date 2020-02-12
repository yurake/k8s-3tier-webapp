package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;


public class ActiveMqResourceTest {

	@Test
	public void testPutcacheError() {
		ActiveMqResource rsc = new ActiveMqResource();
		Response resp = rsc.putcache();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testGetcacheError() {
		ActiveMqResource rsc = new ActiveMqResource();
		Response resp = rsc.getcache();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testPublishError() {
		ActiveMqResource rsc = new ActiveMqResource();
		Response resp = rsc.publish();
		assertThat(resp.getStatus(), is(500));
	}

}
