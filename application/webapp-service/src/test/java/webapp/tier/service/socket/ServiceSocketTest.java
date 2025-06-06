package webapp.tier.service.socket;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.inject.Inject;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ServiceSocketTest {

	@Inject
	ServiceSocket socket;

	private static final String id = "testOnOpen";
	private static final String message = "testMessage";

	@Test
	void testOnOpen() {
		Session session = mock(Session.class);
		try {
			when(session.getId()).thenReturn(id);
			socket.onOpen(session);
			assertThat(socket.getSessions().containsKey(id), is(true));
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnClose() {
		Session session = mock(Session.class);
		try {
			when(session.getId()).thenReturn(id);
			socket.onOpen(session);
			socket.onClose(session);
			assertThat(socket.getSessions().containsKey(id), is(false));
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnError() {
		Session session = mock(Session.class);
		try {
			when(session.getId()).thenReturn(id);
			socket.onOpen(session);
			socket.onError(session, new Throwable("testOnError"));
			assertThat(socket.getSessions().containsKey(id), is(false));
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnMessage() {
		Session session = mock(Session.class);
		try {
			RemoteEndpoint.Async async = mock(RemoteEndpoint.Async.class);
			when(session.getId()).thenReturn(id);
			when(session.getAsyncRemote()).thenReturn(async);
			socket.onOpen(session);
			socket.onMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnMessageNoSubscriber() {
		try {
			socket.onMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
