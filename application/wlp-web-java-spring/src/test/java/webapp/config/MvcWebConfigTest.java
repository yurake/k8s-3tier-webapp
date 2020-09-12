package webapp.config;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

class MvcWebConfigTest {

	@Test
	void testconfigureViewResolvers() {
		ViewResolverRegistry registry = mock(ViewResolverRegistry.class);
		MvcWebConfig cfg = new MvcWebConfig();
		try {
			cfg.configureViewResolvers(registry);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testaddResourceHandlers() {
		ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
		ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);
		when(registration.addResourceLocations(anyString())).thenReturn(registration);
		when(registry.addResourceHandler(anyString())).thenReturn(registration);
		MvcWebConfig cfg = new MvcWebConfig();
		try {
			cfg.addResourceHandlers(registry);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
