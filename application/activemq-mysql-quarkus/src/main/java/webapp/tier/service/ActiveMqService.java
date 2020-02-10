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

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ActiveMqService implements Runnable {

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
		try {
			if (context == null) {
				context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
			}
			if (consumer == null) {
				consumer = context.createConsumer(context.createTopic(topicname));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (consumer != null) {
				consumer.close();
			}
			if (context != null) {
				context.close();
			}
		}
		return consumer;
	}

	@Override
	public void run() {
		String fullmsg = null;
		MysqlService mysqlsvc = new MysqlService();
		try {
			JMSConsumer jmsconsumer = getJmsConnect();
			while (true) {
				LOG.info("Ready for receive message...");
				Message message = jmsconsumer.receive();
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String[] body;
					body = textMessage.getText().split(splitkey, 0);
					fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
					LOG.info(fullmsg);
					mysqlsvc.insertMsg(body);
				} else {
					fullmsg = "No Data";
					LOG.info(fullmsg);
				}
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
