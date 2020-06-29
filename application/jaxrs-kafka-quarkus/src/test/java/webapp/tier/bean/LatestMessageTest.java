
package webapp.tier.bean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class LatestMessageTest {

	@Test
	void testGetLatestMsg() {
		String testmsg = "testmsg";
		LatestMessage.setLatestMsg(testmsg);
		assertThat(testmsg, is(LatestMessage.getLatestMsg()));
	}
}
