package webapp.tier.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RandomServiceTest {

	@Inject
	RandomService svc;

	@Test
	void testDeliverrandomCase0Error() throws Exception {
		assertThat(svc.deliverrandom(0), is("Call: ActiveMQ Publish: Test"));
	}

	@Test
	void testDeliverrandomCase1Error() throws Exception {
		assertThat(svc.deliverrandom(1), is("Call: RabbitMQ Publish: Test"));
	}

	@Test
	void testDeliverrandomCase2Error() throws Exception {
		assertThat(svc.deliverrandom(2), is("Call: Redis Publish: Test"));
	}

	@Test
	void testDeliverrandomCase3Error() throws Exception {
		assertThat(svc.deliverrandom(3), is("Call: Postgres Publish: Test"));
	}

	@Test
	void testDeliverrandomCase4Error() throws Exception {
		assertThat(svc.deliverrandom(4), is("Call: Hazelcast Publish: Test"));
	}

	@Test
	void testDeliverrandomCase5Error() throws Exception {
		assertThat(svc.deliverrandom(5), is("Call: Mongodb Publish: Test"));
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
		int i = svc.getNum(6);
		assertThat(i, greaterThanOrEqualTo(0));
		assertThat(i, lessThanOrEqualTo(5));
	}
}
