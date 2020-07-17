package webapp.tier.service.generator;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MessageGeneratorTest {

	@Test
	void test() {
		MessageGenerator gen = new MessageGenerator();
		gen.generate();
	}

}
