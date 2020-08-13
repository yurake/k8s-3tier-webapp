package webapp.tier.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RandomServiceTest {

	@Inject
	private RandomService svc;

	@Test
	void testDeliverrandomCase0Error() throws Exception {
		assertThat(svc.deliverrandom(0), is("Test"));
	}

	@Test
	void testDeliverrandomCase1Error() throws Exception {
		assertThat(svc.deliverrandom(1), is("Test"));
	}

	@Test
	void testDeliverrandomCase2Error() throws Exception {
		assertThat(svc.deliverrandom(2), is("Test"));
	}

	@Test
	void testDeliverrandomCase3Error() throws Exception {
		assertThat(svc.deliverrandom(3), is("Test"));
	}

	@Test
	void testDeliverrandomCase4Error() throws Exception {
		assertThat(svc.deliverrandom(4), is("Test"));
	}

	@Test
	void testDeliverrandomCase5Error() throws Exception {
		assertThat(svc.deliverrandom(5), is("Test"));
	}

	@Test
	void testDeliverrandomError() throws Exception {
		Throwable exception = assertThrows(IllegalArgumentException.class,
				() -> {
					svc.deliverrandom(9999);
				});
		assertThat(exception.getMessage(), is("random error"));
	}

	@Test
	void testGetNum() {
		Integer i = svc.getNum(6);
		assertThat(i, greaterThanOrEqualTo(0));
		assertThat(i, lessThanOrEqualTo(5));
	}
}
