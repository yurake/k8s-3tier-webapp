package webapp.tier.service.socket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/quarkus/redis/subscribe")
@ApplicationScoped
public class RedisSocket extends ServiceSocket{
}
