package webapp.tier.service.socket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/quarkus/activemq/subscribe")
@ApplicationScoped
public class ActiveMqSocket {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	Map<String, Session> sessions = new ConcurrentHashMap<>();

	public Map<String, Session> getSessions() {
		return this.sessions;
	}

	@OnOpen
	public void onOpen(Session session) {
		sessions.put(session.getId(), session);
		logger.log(Level.INFO, "open : {0}", session.getId());
	}

	@OnClose
	public void onClose(Session session) {
		sessions.remove(session.getId());
		logger.log(Level.INFO, "close : {0}", session.getId());
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		sessions.remove(session.getId());
		logger.logp(Level.SEVERE, "error : {0}{1}", session.getId(),
				throwable.getMessage());
	}

	@OnMessage
	public void onMessage(String message) {
		if (sessions.isEmpty()) {
			logger.log(Level.INFO, "No Subscriber");
		} else {
			sessions.values().forEach(s -> {
				logger.log(Level.INFO, "Send : {0}", s.getId());
				s.getAsyncRemote().sendObject(message, result -> {
					if (result.getException() != null) {
						logger.log(Level.SEVERE, "Unable to send message",
								result.getException());
					}
				});
			});
		}
	}
}
