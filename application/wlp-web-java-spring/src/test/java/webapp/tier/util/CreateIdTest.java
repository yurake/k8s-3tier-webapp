package webapp.tier.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

public class CreateIdTest {

	@Test
	public void testCreateid() {
		assertThat(5, is(String.valueOf(CreateId.createid()).length()));
	}

	@Test
	public void testCreatewideid() {
		assertThat(5, is(String.valueOf(CreateId.createwideid()).length()));
	}

}
