package webapp.tier.service.socket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/quarkus/rabbitmq/subscribe")
@ApplicationScoped
public class RabbitmqSocket extends ServiceSocket {
}
