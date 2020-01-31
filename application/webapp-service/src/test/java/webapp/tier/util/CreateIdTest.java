package webapp.tier.util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

class CreateIdTest {

	@Test
	void testcreateid() {
		int testid = CreateId.createid();
		assertThat(testid, is(notNullValue()));
	}

}
