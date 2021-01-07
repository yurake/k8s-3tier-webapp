package webapp.tier.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class GetConfigTest {

	@Test
	public void testGetResourceBundle() {
		assertThat("Hello k8s-3tier-webapp!", is(GetConfig.getResourceBundle("common.message")));
	}

}
