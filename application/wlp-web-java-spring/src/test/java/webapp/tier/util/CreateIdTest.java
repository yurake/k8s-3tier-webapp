package webapp.tier.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

public class CreateIdTest {

	@Test
	public void testCreateid() throws NoSuchAlgorithmException {
		assertThat(5, is(String.valueOf(CreateId.createid()).length()));
	}

	@Test
	public void testCreatewideid() {
		assertThat(5, is(String.valueOf(CreateId.createwideid()).length()));
	}

}
