package webapp.tier.service.subscribe;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import redis.clients.jedis.JedisPubSub;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.DeliverService;
import webapp.tier.service.MysqlService;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class RedisSubscriber extends JedisPubSub {

	MysqlService mysqlsvc = new MysqlService();
	private static final Logger LOG = Logger.getLogger(RedisSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("redis.splitkey", String.class);

	@Override
	public void onMessage(String channel, String message) {
		String errormsg = "Subscribe Error.";
		MsgBeanUtils msgbean = new MsgBeanUtils();
		MsgBean bean = msgbean.splitBody(message, splitkey);
		msgbean.setFullmsgWithType(bean, "Received");
		LOG.info(msgbean.getFullmsg());

		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();

		try {
			mysqlsvc.insertMsg(bean);
			LOG.info("Call: Random Publish");
			LOG.info(deliversvc.random());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, errormsg, e);
			throw new RuntimeException(errormsg);
		}
	}

}
