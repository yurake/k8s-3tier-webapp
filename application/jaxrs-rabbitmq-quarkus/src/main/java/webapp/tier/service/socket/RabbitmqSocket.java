package webapp.tier.service.socket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/quarkus/rabbitmq/subscribe")
@ApplicationScoped
public class RabbitmqSocket extends ServiceSocket{
}
