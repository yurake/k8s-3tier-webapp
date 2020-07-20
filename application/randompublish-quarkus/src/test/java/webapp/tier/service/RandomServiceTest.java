package webapp.tier.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RandomServiceTest {

	@Test
	void testDeliverrandomCase0Error() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 0;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverrandomCase1Error() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 1;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverrandomCase2Error() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 2;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverrandomCase3Error() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 3;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverrandomCase4Error() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 4;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverrandomCase5Error() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 5;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverrandomError() {
		RandomService srv = new RandomService() {
			protected int getNum(Integer i) {
				return 9999;
			}
		};
		try {
			srv.deliverrandom();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetNum() {
		RandomService svc = new RandomService();
		Integer i = svc.getNum(6);
		assertThat(i, greaterThanOrEqualTo(0));
		assertThat(i, lessThanOrEqualTo(5));
	}
}

