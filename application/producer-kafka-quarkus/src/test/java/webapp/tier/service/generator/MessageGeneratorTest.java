package webapp.tier.service.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MessageGeneratorTest {

	@Test
	void test() {
		MessageGenerator gen = new MessageGenerator();
		try {
			gen.generate();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
