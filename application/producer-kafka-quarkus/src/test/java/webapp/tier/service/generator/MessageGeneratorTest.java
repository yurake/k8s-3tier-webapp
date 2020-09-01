package webapp.tier.service.generator;

import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MessageGeneratorTest {

	@Inject
	private MessageGenerator gen;

	@Test
	void testGenerate() {
		try {
			gen.generate();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
