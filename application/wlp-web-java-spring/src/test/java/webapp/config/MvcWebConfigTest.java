package webapp.config;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

class MvcWebConfigTest {

	@Test
	void test() {
		ViewResolverRegistry registry = mock(ViewResolverRegistry.class);
		MvcWebConfig cfg = new MvcWebConfig();
		try {
			cfg.configureViewResolvers(registry);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
