package webapp.tier.service.socket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/quarkus/hazelcast/subscribe")
@ApplicationScoped
public class HazelcastSocket extends ServiceSocket{
}
