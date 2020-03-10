package webapp.tier.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class ActiveMqService implements Runnable {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private static final Logger LOG = Logger.getLogger(ActiveMqService.class.getSimpleName());
	private static String topicname = ConfigProvider.getConfig().getValue("activemq.topic.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq.split.key", String.class);
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	private static JMSContext context = null;
	private static JMSConsumer consumer = null;

	@Inject
	ConnectionFactory connectionFactory;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		LOG.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		LOG.info("The application is stopping...");
	}

	private JMSConsumer getJmsConnect() {
		if (context == null) {
			context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
		}
		if (consumer == null) {
			consumer = context.createConsumer(context.createTopic(topicname));
		}
		return consumer;
	}

	@Override
	public void run() {
		MysqlService mysqlsvc = new MysqlService();
		try {
			JMSConsumer jmsconsumer = getJmsConnect();
			while (true) {
				LOG.info("Ready for receive message...");
				Message message = jmsconsumer.receive();

				MsgBeanUtils msgbean = new MsgBeanUtils();
				TextMessage textMessage = (TextMessage) message;
				MsgBean bean = msgbean.splitBody(textMessage.getText(), splitkey);
				msgbean.setFullmsgWithType(bean, "Received");
				LOG.info(msgbean.getFullmsg());

				mysqlsvc.insertMsg(bean);
				LOG.info("Call: Random Publish");
				LOG.info(deliversvc.random());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isActive() {
		boolean status = false;
		try {
			JMSConsumer jmsconsumer = getJmsConnect();
			jmsconsumer.getMessageListener();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
