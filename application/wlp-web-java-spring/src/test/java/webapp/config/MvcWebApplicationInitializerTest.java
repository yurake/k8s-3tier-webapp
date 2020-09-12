package webapp.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.ServletRegistration;

import org.junit.jupiter.api.Test;

class MvcWebApplicationInitializerTest {

	@Test
	void testgetRootConfigClasses() {
		MvcWebApplicationInitializer init = new MvcWebApplicationInitializer();
		assertThat(init.getRootConfigClasses(), nullValue());
	}

	@Test
	void testgetServletConfigClasses() {
		MvcWebApplicationInitializer init = new MvcWebApplicationInitializer();
		assertThat(init.getServletConfigClasses(), is(new Class[] { MvcWebConfig.class }));
	}

	@Test
	void testgetServletMappings() {
		MvcWebApplicationInitializer init = new MvcWebApplicationInitializer();
		assertThat(init.getServletMappings(), is(new String[] { "/" }));
	}

	@Test
	void testcustomizeRegistration() {
		ServletRegistration.Dynamic dynamic = mock(ServletRegistration.Dynamic.class);
		when(dynamic.setInitParameter(anyString(), anyString())).thenReturn(true);
		MvcWebApplicationInitializer init = new MvcWebApplicationInitializer();
		try {
			init.customizeRegistration(dynamic);
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	void testcustomizeRegistrationError() {
		ServletRegistration.Dynamic dynamic = mock(ServletRegistration.Dynamic.class);
		when(dynamic.setInitParameter(anyString(), anyString())).thenReturn(false);
		MvcWebApplicationInitializer init = new MvcWebApplicationInitializer();
		try {
			init.customizeRegistration(dynamic);
			fail();
		} catch (Exception e) {
		}
	}

}
