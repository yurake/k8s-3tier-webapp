package webapp.tier.service.socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RedisSocketTestForQuarkus {

	@Inject
	RedisSocket socket;

	String id = "testOnOpen";
	String message = "testMessage";

	private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

	@TestHTTPResource("/quarkus/redis/subscribe")
	URI uri;

	static Session mastersession;

	@Test
	public void testOmMessage() throws Exception {
		try (Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)) {
			mastersession = session;
			assertThat(MESSAGES.poll(10, TimeUnit.SECONDS), is("CONNECT"));

			SendHandler sendHandler = new SendHandler() {
				@Override
				public void onResult(SendResult result) {
					assertOK(result);
				}

				private void assertOK(SendResult result) {
					assertThat("session sendHandler result ok: ", result.isOK());
				}
			};
			session.getAsyncRemote().sendObject("Test", sendHandler);
			assertThat(MESSAGES.poll(10, TimeUnit.SECONDS), is("Test"));
		}
	}

	@ClientEndpoint
	public static class Client {

		@OnOpen
		public void open(Session session) {
			MESSAGES.add("CONNECT");
		}

		@OnMessage
		void message(String msg) {
			MESSAGES.add(msg);
		}
	}
}
