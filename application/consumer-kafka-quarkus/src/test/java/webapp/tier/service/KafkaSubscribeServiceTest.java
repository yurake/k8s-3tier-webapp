package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.annotations.Merge;

@QuarkusTest
class KafkaSubscribeServiceTest {

	@Inject
	private KafkaSubscribeService svc;

	@Inject
	@Channel("message")
	@Merge(Merge.Mode.MERGE)
	private Emitter<String> emitmsg;

	@Test
	void testProcess() {
		try {
			svc.process("Test");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testProcessEmitter() {
		emitmsg.send("Test");
	}

}
