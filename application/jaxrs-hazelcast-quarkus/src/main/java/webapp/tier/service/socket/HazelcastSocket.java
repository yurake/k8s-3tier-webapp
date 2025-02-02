package webapp.tier.service.socket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/quarkus/hazelcast/subscribe")
@ApplicationScoped
public class HazelcastSocket extends ServiceSocket {
}
