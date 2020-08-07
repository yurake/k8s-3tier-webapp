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
class ServiceSocketTest {

	@Inject
	private ServiceSocket socket;

	private static final String id = "testOnOpen";
	private static final String message = "testMessage";

	@Test
	void testOnOpen() {
		Session session = Mockito.mock(Session.class);
		try {
			Mockito.when(session.getId()).thenReturn(id);
			socket.onOpen(session);
			assertThat(socket.getSessions().containsKey(id), is(true));
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnClose() {
		Session session = Mockito.mock(Session.class);
		try {
			Mockito.when(session.getId()).thenReturn(id);
			socket.onOpen(session);
			socket.onClose(session);
			assertThat(socket.getSessions().containsKey(id), is(false));
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnError() {
		Session session = Mockito.mock(Session.class);
		try {
			Mockito.when(session.getId()).thenReturn(id);
			socket.onOpen(session);
			socket.onError(session, new Throwable("testOnError"));
			assertThat(socket.getSessions().containsKey(id), is(false));
		} finally {
			socket.onClose(session);
		}
	}

	@Test
	void testOnMessage() {
		Session session = Mockito.mock(Session.class);
		try {
			RemoteEndpoint.Async async = Mockito.mock(RemoteEndpoint.Async.class);
			Mockito.when(session.getId()).thenReturn(id);
			Mockito.when(session.getAsyncRemote()).thenReturn(async);
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
