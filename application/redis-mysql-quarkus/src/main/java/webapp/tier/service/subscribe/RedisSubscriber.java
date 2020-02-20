package webapp.tier.service.subscribe;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import redis.clients.jedis.JedisPubSub;
import webapp.tier.service.DeliverService;
import webapp.tier.service.MysqlService;

@ApplicationScoped
public class RedisSubscriber extends JedisPubSub {

	MysqlService mysqlsvc = new MysqlService();
	private static final Logger LOG = Logger.getLogger(RedisSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);

	@Override
	public void onMessage(String channel, String message) {

		String[] body = message.split(splitkey, 0);
		String fullmsg = "Received channel:" + channel + ", id: " + body[0] + ", msg: " + body[1];
		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();

		try {
			LOG.info(fullmsg);
			mysqlsvc.insertMsg(body);

			String response;
			LOG.info("Call: Random Publish");
			response = deliversvc.random();
			LOG.info(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
