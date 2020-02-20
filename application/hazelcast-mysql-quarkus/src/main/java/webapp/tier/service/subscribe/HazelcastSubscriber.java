package webapp.tier.service.subscribe;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import webapp.tier.service.DeliverService;
import webapp.tier.service.MysqlService;

@ApplicationScoped
public class HazelcastSubscriber implements MessageListener<String> {

	MysqlService mysqlsvc = new MysqlService();
	private static final Logger LOG = Logger.getLogger(HazelcastSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	@Override
	public void onMessage(Message<String> message) {

		String[] body = message.getMessageObject().split(splitkey, 0);
		String fullmsg = "Received id:" + body[0] + ", msg: " + body[1];
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
