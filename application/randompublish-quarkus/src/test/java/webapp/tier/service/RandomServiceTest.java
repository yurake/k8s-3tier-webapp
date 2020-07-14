package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RandomServiceTest {

	@Test
	void testDeliverrandom() {
		RandomService srv = new RandomService();
		int counts = 50;
		for (int i = 0; i < counts; i++) {
			try {
				srv.deliverrandom();
				fail();
			} catch (Exception e) {
			}
		}
	}

}
