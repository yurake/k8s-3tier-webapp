package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheckResponse.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.hazelcast.HazelcastServerTestResource;
import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;
import webapp.tier.healthcheck.ReadinessHealthCheckHazelcastSubscriber;
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

	@BeforeAll
	public static void init() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getNetworkConfig().addAddress(address);
		clientConfig.getConnectionStrategyConfig().getConnectionRetryConfig()
				.setClusterConnectTimeoutMillis(5000)
				.setMaxBackoffMillis(10000);
		mockInstance = HazelcastClient.newHazelcastClient(clientConfig);
	}

	private MsgBean publishMsg() {
		MsgBean msgbean = errormsg;

		try {
			msgbean = new MsgBean(CreateId.createid(), respbody, "Publish");
			String body = MsgUtils.createBody(msgbean, splitkey);
			logger.log(Level.INFO, "Body is: " + body);
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

	@Test
	void testSubscribeHazelcast() {
		try {
			publishMsg();
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testSubscribeAndReadinessHealthCheck() {
		ReadinessHealthCheckHazelcastSubscriber hc = new ReadinessHealthCheckHazelcastSubscriber();
		assertEquals(Status.UP, hc.call().getStatus(), "Unexpected status");

		try {
			publishMsg();
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
