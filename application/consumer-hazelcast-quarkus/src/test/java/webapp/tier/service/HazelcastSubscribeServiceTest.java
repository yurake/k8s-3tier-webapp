package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.hazelcast.HazelcastServerTestResource;
import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@QuarkusTest
@QuarkusTestResource(HazelcastServerTestResource.class)
class HazelcastSubscribeServiceTest {

	@Inject
	HazelcastSubscribeService svc;

	@ConfigProperty(name = "hazelcast.topic.name")
	String topicname;

	@ConfigProperty(name = "hazelcast.split.key")
	String splitkey;

	private static String address = ConfigProvider.getConfig().getValue(
			"hazelcast.address", String.class);

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	private static HazelcastInstance mockInstance;
	private String respbody = "message: Hello k8s-3tier-webapp with quarkus";
	private static MsgBean errormsg = new MsgBean(0, "Unexpected Error");

	public HazelcastSubscribeServiceTest() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		mockInstance = HazelcastClient.newHazelcastClient(clientConfig);
	}
	
	/**
	@BeforeEach
	public void setup() throws IOException {
		mockInstance = Hazelcast.newHazelcastInstance();
	}

	@AfterEach
	public void after() {
		mockInstance.shutdown();
	}
	**/

	/**
	@Test
	void testSubscribeHazelcast() {
		svc.subscribeHazelcast(mockInstance, svc.createHazelcastDeliverSubscriber());
		assertThat(publishMsg(mockInstance).getFullmsg(), containsString(respbody));
	}
	**/
	
	@Test
	void testSubscribeHazelcast() {
		//HazelcastSubscribeService svc = new HazelcastSubscribeService(mockInstance);
		publishMsg();
		svc.terminate();
	}

	private MsgBean publishMsg() {
		MsgBean msgbean = errormsg;

		try {
			msgbean = new MsgBean(CreateId.createid(), respbody, "Publish");
			String body = MsgUtils.createBody(msgbean, splitkey);
			logger.log(Level.INFO, "Body is: "+ body);
			ITopic<Object> topic = mockInstance.getTopic(topicname);
			topic.publish(body);
		} catch (IllegalStateException | NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "Publish Error.", e);
			e.printStackTrace();
		} finally {
			if (mockInstance != null) {
				mockInstance.shutdown();
			}
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}
}
