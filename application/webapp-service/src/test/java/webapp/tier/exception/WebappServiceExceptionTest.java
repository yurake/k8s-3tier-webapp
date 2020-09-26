package webapp.tier.exception;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class WebappServiceExceptionTest {

	@Test
	void testWebappServiceException() {
		WebappServiceException e = new WebappServiceException("Error");
		assertThat(e.getClass(), is(WebappServiceException.class));
		assertThat(e.getMessage(), is("Error"));
	}

	@Test
	void testWebappServiceExceptionThrowable() {
		RuntimeException ex = new RuntimeException();
		WebappServiceException e = new WebappServiceException("Error", ex);
		assertThat(e.getClass(), is(WebappServiceException.class));
		assertThat(e.getMessage(), is("Error"));
	}

}
