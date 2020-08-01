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
	public void testWebsocketChat() throws Exception {
		try (Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)) {
			mastersession = session;
			assertThat(MESSAGES.poll(10, TimeUnit.SECONDS), is("CONNECT"));
			session.getAsyncRemote().sendObject("Test");
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
