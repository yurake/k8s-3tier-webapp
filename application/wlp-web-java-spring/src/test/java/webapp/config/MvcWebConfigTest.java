package webapp.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

class MvcWebConfigTest {

	@Test
	void test() {
		ViewResolverRegistry registry = mock(ViewResolverRegistry.class);
		MvcWebConfig cfg = new MvcWebConfig();
		cfg.configureViewResolvers(registry);
		fail("まだ実装されていません");
	}

}
