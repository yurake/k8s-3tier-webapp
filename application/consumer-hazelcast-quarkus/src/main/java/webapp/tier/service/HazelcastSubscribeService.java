package webapp.tier.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastSubscribeService extends HazelcastConnectionHelper implements Runnable {

	private static final Logger LOG = Logger.getLogger(HazelcastSubscribeService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topic.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("The application is stopping...");
	}

	public boolean isActive() {
		HazelcastInstance client = null;
		boolean status = false;
		try {
			client = getInstance();
			status = client.getLifecycleService().isRunning();
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, "Connect Error.", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return status;
	}

	@Override
	public void run() {
		try {
			HazelcastInstance client = getInstance();
			ITopic<Object> topic = client.getTopic(topicname);
			topic.addMessageListener(new MessageListenerImpl());
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, "Subscribe Error.", e);
		}
	}

    private static class MessageListenerImpl implements MessageListener<Object> {
    	@Override
    	public void onMessage(Message<Object> message) {
    		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(), splitkey);
    		msgbean.setFullmsg("Received");
    		LOG.info(msgbean.getFullmsg());
    		DeliverService deliversvc = CDI.current().select(DeliverService.class, RestClient.LITERAL).get();
    		String response = deliversvc.random();
    		LOG.log(Level.INFO, "Call Random Publish: {0}", response);
    	}
    }
}
