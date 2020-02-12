package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class PostgresResourceTest {

	@Test
	public void testInsertError() {
		PostgresResource rsc = new PostgresResource();
		Response resp = rsc.insert();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testSelectError() {
		PostgresResource rsc = new PostgresResource();
		Response resp = rsc.select();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testDeleteError() {
		PostgresResource rsc = new PostgresResource();
		Response resp = rsc.delete();
		assertThat(resp.getStatus(), is(500));
	}

}
