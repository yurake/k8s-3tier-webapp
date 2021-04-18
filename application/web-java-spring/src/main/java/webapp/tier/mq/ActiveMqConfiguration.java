package webapp.tier.mq;

import javax.jms.Queue;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class ActiveMqConfiguration {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Value("${spring.activemq.broker-url}")
	private String url;

	@Value("${spring.activemq.queue.name}")
	private String queuename;

//	@Bean
//	public ConnectionFactory connectionFactory() throws JMSException {
//		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
//		logger.info("Set Broker URL: " + url);
//		return connectionFactory;
//	}
//
//	@Bean
//	public JmsTemplate jmsTemplate() throws JMSException {
//		JmsTemplate template = new JmsTemplate();
//		template.setConnectionFactory(connectionFactory());
//		return template;
//	}

	@Bean
	Queue queue() {
		logger.info("Set Queue: " + queuename);
		return new ActiveMQQueue(queuename);
	}
}
