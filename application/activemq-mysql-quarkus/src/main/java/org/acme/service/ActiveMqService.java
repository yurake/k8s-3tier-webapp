package org.acme.service;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.acme.util.GetConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class ActiveMqService implements Runnable {

	Logger logger = LoggerFactory.getLogger(ActiveMqService.class);
	private static String splitkey = GetConfig.getResourceBundle("activemq.split.key");
	private static String topicname = GetConfig.getResourceBundle("activemq.topic.name");
	Connection con = null;
	QueueConnection qcon = null;
	private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

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

	@Override
	public void run() {
		String fullmsg = null;
		MysqlService mysqlsvc = new MysqlService();

		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			JMSConsumer consumer = context.createConsumer(context.createQueue(topicname));
			while (true) {
				logger.info("Ready for receive message...");
				Message message = consumer.receive();
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String[] body = textMessage.getText().split(splitkey, 0);
					fullmsg = "Received id: " + body[0] + ", msg: " + body[1];
					logger.info(fullmsg);
					mysqlsvc.insertMysql(body);
				} else {
					fullmsg = "No Data";
					logger.info(fullmsg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isActive() {
		boolean status = false;
		try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
			context.createConsumer(context.createQueue(topicname));
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
