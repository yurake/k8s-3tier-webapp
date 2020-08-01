package webapp.tier.service.socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RedisSocketTest {

	@Inject
	RedisSocket socket;

	String id = "testOnOpen";
	String message = "testMessage";

	@Test
	void testOnOpen() {
		Session session = Mockito.mock(Session.class);
		Mockito.when(session.getId()).thenReturn(id);
		socket.onOpen(session);
		assertThat(socket.getSessions().containsKey(id), is(true));
	}

	@Test
	void testOnClose() {
		Session session = Mockito.mock(Session.class);
		Mockito.when(session.getId()).thenReturn(id);
		socket.onOpen(session);
		socket.onClose(session);
		assertThat(socket.getSessions().containsKey(id), is(false));
	}

	@Test
	void testOnError() {
		Session session = Mockito.mock(Session.class);
		Mockito.when(session.getId()).thenReturn(id);
		socket.onOpen(session);
		socket.onError(session, new Throwable("testOnError"));
		assertThat(socket.getSessions().containsKey(id), is(false));
	}

	@Test
	void testOnMessage() {
		Session session = Mockito.mock(Session.class);
		RemoteEndpoint.Async async = Mockito.mock(RemoteEndpoint.Async.class);
		Mockito.when(session.getId()).thenReturn(id);
		Mockito.when(session.getAsyncRemote()).thenReturn(async);
		socket.onOpen(session);
		try {
			socket.onMessage(message);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		} finally {
			socket.onClose(session);
		}
	}
	@Test
	void testOnMessageNoSubscriber() {
		try {
			socket.onMessage(message);
		} catch (Exception expected) {
			expected.printStackTrace();
			fail();
		}
	}
}
