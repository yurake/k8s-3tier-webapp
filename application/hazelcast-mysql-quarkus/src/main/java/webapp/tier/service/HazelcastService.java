package webapp.tier.service;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

@ApplicationScoped
public class HazelcastService {
	Logger logger = LoggerFactory.getLogger(HazelcastService.class);
	private static String topicname = ConfigProvider.getConfig().getValue("hazelcast.topic.name", String.class);
	private static String splitkey = ConfigProvider.getConfig().getValue("hazelcast.split.key", String.class);

	public boolean isActive() {
		HazelcastInstance client = null;
		boolean status = false;
		try {
			client = ConnectHazelcast.getInstance();
			status = client.getLifecycleService().isRunning();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.shutdown();
		}
		return status;
	}

	public String subscribeHazelcast() {
		String fullmsg = null;
		HazelcastInstance client = null;
		MysqlService mysqlsvc = new MysqlService();

		try {
			client = ConnectHazelcast.getInstance();
			ITopic<String> topic = client.getTopic(topicname);

			topic.addMessageListener(new MessageListener<String>() {
				@Override
				public void onMessage(Message<String> message) {
					String fullmsg = null;
					String[] body = message.getMessageObject().split(splitkey, 0);
					fullmsg = "Received id:" + body[0] + ", msg: " + body[1];
					logger.info(fullmsg);
					mysqlsvc.insertMysql(body);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullmsg;
	}
}
