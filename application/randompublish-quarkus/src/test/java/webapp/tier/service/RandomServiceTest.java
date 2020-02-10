package webapp.tier.service;

import org.junit.jupiter.api.Test;

class RandomServiceTest {

	@Test
	void testDeliverrandom() {
		RandomService srv = new RandomService();
		int counts = 10;
		for (int i = 0; i < counts; i++) {
			try {
				srv.deliverrandom();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
