package webapp.tier.util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

class CreateIdTest {

	@Test
	void testCreateid() {
		int expected = CreateId.createid();
		assertThat(expected, is(notNullValue()));
	}

	@Test
	void testCreatewideid() {
		int expected = CreateId.createwideid();
		assertThat(expected, is(notNullValue()));
	}
}
