package webapp.tier.service.socket;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.SendHandler;
import jakarta.websocket.SendResult;
import jakarta.websocket.Session;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ActiveMqSocketForQuarkusTest {

	private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

	@TestHTTPResource("/quarkus/activemq/subscribe")
	URI uri;

	@Test
	void testOnMessage() throws Exception {
		try (Session session = ContainerProvider.getWebSocketContainer()
				.connectToServer(Client.class, uri)) {
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
		public void message(String msg) {
			MESSAGES.add(msg);
		}
	}
}
