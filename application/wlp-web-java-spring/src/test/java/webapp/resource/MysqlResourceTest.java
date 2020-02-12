package webapp.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

public class MysqlResourceTest {

	@Test
	public void testInsertError() {
		MysqlResource rsc = new MysqlResource();
		Response resp = rsc.insert();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testSelectError() {
		MysqlResource rsc = new MysqlResource();
		Response resp = rsc.select();
		assertThat(resp.getStatus(), is(500));
	}

	@Test
	public void testDeleteError() {
		MysqlResource rsc = new MysqlResource();
		Response resp = rsc.delete();
		assertThat(resp.getStatus(), is(500));
	}

}
