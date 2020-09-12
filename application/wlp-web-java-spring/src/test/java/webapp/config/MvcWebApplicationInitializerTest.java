package webapp.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
		} catch (Exception e) {
			e.printStackTrace();
			fail();
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
			e.printStackTrace();
		}
	}

}
