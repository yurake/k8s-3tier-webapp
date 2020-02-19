package webapp.tier.service;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

@ApplicationScoped
public class HazelcastService {

	@Inject
	@RestClient
	DeliverService deliversvc;

	private static final Logger LOG = Logger.getLogger(HazelcastService.class.getSimpleName());
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
			if (client != null) {
				client.shutdown();
			}
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
					LOG.info(fullmsg);
					try {
						mysqlsvc.insertMsg(body);

						String response;
						LOG.info("Call: Random Publish");
						response = deliversvc.random();
						LOG.info(response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullmsg;
	}
}
