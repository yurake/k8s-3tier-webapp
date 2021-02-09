package webapp.tier.util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class CreateIdTest {

	@Test
	void testCreateid() {
		int expected;
		try {
			expected = CreateId.createid();
			assertThat(expected, is(notNullValue()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testCreatewideid() {
		int expected = CreateId.createwideid();
		assertThat(expected, is(notNullValue()));
	}
}
