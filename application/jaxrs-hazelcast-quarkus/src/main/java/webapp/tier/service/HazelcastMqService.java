package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.ConfigProvider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.service.socket.HazelcastSocket;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastMqService extends HazelcastService implements Runnable {

	private static final Logger LOG = Logger.getLogger(HazelcastMqService.class.getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String queuename = ConfigProvider.getConfig().getValue("hazelcast.queue.name", String.class);
	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topicname.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("Subscribe is stopping...");
	}

	public MsgBean putMsg(HazelcastInstance client) throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Put");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try {
			BlockingQueue<Object> queue = client.getQueue(queuename);
			queue.put(body);
		} catch (IllegalStateException | InterruptedException e) {
			LOG.log(Level.SEVERE, "Put Error.", e);
		    Thread.currentThread().interrupt();
			throw new RuntimeException("Put Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(HazelcastInstance client) throws RuntimeException {
		MsgBean msgbean = null;

		try {
			BlockingQueue<String> queue = client.getQueue(queuename);
			Object resp = queue.poll();

			if (Objects.isNull(resp) || resp.toString().isEmpty()) {
				msgbean = new MsgBean(0, "No Data.", "Get");
			} else {
				msgbean = MsgUtils.splitBody(resp.toString(), splitkey);
				msgbean.setFullmsg("Get");
			}
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, "Get Error.", e);
			throw new RuntimeException("Get Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean publishMsg(HazelcastInstance client) throws RuntimeException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Publish");
		String body = MsgUtils.createBody(msgbean, splitkey);

		try {
			ITopic<Object> topic = client.getTopic(topicname);
			topic.publish(body);
		} catch (IllegalStateException e) {
			LOG.log(Level.SEVERE, "Publish Error.", e);
			throw new RuntimeException("Publish Error.");
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
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
    	HazelcastSocket hazsock = new HazelcastSocket();
    	@Override
    	public void onMessage(Message<Object> message) {
    		MsgBean msgbean = MsgUtils.splitBody(message.getMessageObject().toString(), splitkey);
			msgbean.setFullmsg("Received");
			LOG.log(Level.INFO, msgbean.getFullmsg());
			hazsock.onMessage(MsgUtils.createBody(msgbean, splitkey));
			msgbean.setFullmsg("Broadcast");
			LOG.log(Level.INFO, msgbean.getFullmsg());
    	}
    }
}
