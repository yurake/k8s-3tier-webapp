package webapp.tier.service.subscribe;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import webapp.tier.bean.MsgBean;
import webapp.tier.service.DeliverService;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class HazelcastSubscriber implements MessageListener<String> {

	private static final Logger LOG = Logger.getLogger(HazelcastSubscriber.class.getSimpleName());
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	@Override
	public void onMessage(Message<String> message) {

		MsgBeanUtils msgbean = new MsgBeanUtils();
		MsgBean bean = msgbean.splitBody(message.getMessageObject(), splitkey);
		msgbean.setFullmsgWithType(bean, "Received");
		LOG.info(msgbean.getFullmsg());

		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();

		LOG.info("Call: Random Publish");
		LOG.info(deliversvc.random());
	}

}
