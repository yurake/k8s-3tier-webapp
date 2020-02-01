package webapp.tier.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.TopicSession;

import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ActiveMqService implements Runnable {

	Logger logger = LoggerFactory.getLogger(ActiveMqService.class);
	private static String topicname = ConfigProvider.getConfig().getValue("activemq.topic.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("activemq.split.key", String.class);
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();
	private static JMSContext context = null;
	private static JMSConsumer consumer = null;

	@Inject
	ConnectionFactory connectionFactory;

	void onStart(@Observes StartupEvent ev) {
		scheduler.submit(this);
		logger.info("The application is starting...");
	}

	void onStop(@Observes ShutdownEvent ev) {
		scheduler.shutdown();
		logger.info("The application is stopping...");
	}

	private JMSConsumer getJmsConnect() {
		try {
			if (context == null) {
				context = connectionFactory.createContext(TopicSession.AUTO_ACKNOWLEDGE);
			}
			if (consumer == null) {
				consumer = context.createConsumer(context.createTopic(topicname));
			}
		} catch (Exception e) {
			e.printStackTrace();
			consumer.close();
			context.close();
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
				logger.info("Ready for receive message...");
				Message message = jmsconsumer.receive();
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String[] body;
					body = textMessage.getText().split(splitkey, 0);
					fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
					logger.info(fullmsg);
					mysqlsvc.insertMysql(body);
				} else {
					fullmsg = "No Data";
					logger.info(fullmsg);
				}
			}
		} catch (JMSException e) {
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
