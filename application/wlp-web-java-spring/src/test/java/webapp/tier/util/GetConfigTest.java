package webapp.tier.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class GetConfigTest {

	@Test
	public void testGetResourceBundle() {
		assertThat("Hello k8s-3tier-webapp!", is(GetConfig.getResourceBundle("common.message")));
	}

}
