package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class RabbitmqResourceTest {

	@Test
	public void testSetError() {
		RabbitmqResource rsc = new RabbitmqResource();
		Response resp = rsc.set();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testGetError() {
		RabbitmqResource rsc = new RabbitmqResource();
		Response resp = rsc.get();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testPublishError() {
		RabbitmqResource rsc = new RabbitmqResource();
		Response resp = rsc.publish();
		assertThat(resp.getStatus(), is(500));
	}

}
