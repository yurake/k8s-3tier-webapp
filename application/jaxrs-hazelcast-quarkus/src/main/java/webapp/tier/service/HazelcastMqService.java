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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class HazelcastMqService implements Runnable {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	private static MsgBean errormsg = new MsgBean(0, "Unexpected Error");

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "hazelcast.queue.name")
	String queuename;

	@ConfigProperty(name = "hazelcast.topic.name")
	String topicname;

	@ConfigProperty(name = "hazelcast.split.key")
	String splitkey;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.info("Subscribe is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.info("Subscribe is stopping...");
	}

	protected HazelcastMessageListener createHazelcastMessageListener() {
		return new HazelcastMessageListener();
	}

	public MsgBean putMsg(HazelcastInstance client) {
		MsgBean msgbean = errormsg;
		try {
			msgbean = new MsgBean(CreateId.createid(), message, "Put");
			String body = MsgUtils.createBody(msgbean, splitkey);
			BlockingQueue<Object> queue = client.getQueue(queuename);
			queue.put(body);
		} catch (IllegalStateException | InterruptedException
				| NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Put Error.", e);
			Thread.currentThread().interrupt();
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean getMsg(HazelcastInstance client) {
		MsgBean msgbean = errormsg;
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
			logger.log(Level.SEVERE, "Get Error.", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	public MsgBean publishMsg(HazelcastInstance client) {
		MsgBean msgbean = errormsg;

		try {
			msgbean = new MsgBean(CreateId.createid(), message, "Publish");
			String body = MsgUtils.createBody(msgbean, splitkey);
			ITopic<Object> topic = client.getTopic(topicname);
			topic.publish(body);
		} catch (IllegalStateException | NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Publish Error.", e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public void run() {
		subscribeHazelcast(HazelcastService.getInstance(),
				createHazelcastMessageListener());
	}

	protected void subscribeHazelcast(HazelcastInstance client,
			HazelcastMessageListener listener) {
		ITopic<Object> topic = client.getTopic(topicname);
		topic.addMessageListener(listener);
	}

}
