package webapp.tier.mq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.github.fridujo.rabbitmq.mock.MockConnection;
import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class RabbitmqServiceTest {

	private MockConnection conn;

	@InjectMocks
	ConnectionFactory cf;

	@BeforeEach
	private void createRabbitmqMock() {
		conn = new MockConnectionFactory().newConnection();
	}

	@AfterEach
	private void closeRabbitmqMock() {
		if (Objects.nonNull(conn)) {
			conn.close();
		}
	}

	@Test
	void testisActiveTrue() {
		RabbitmqService svc = new RabbitmqService() {
			public Connection getConnection() {
				return conn;
			}
		};
		Boolean result = svc.isActive();
		assertThat(result, is(true));
	}

	@Test
	void testisActiveFalse() {
		RabbitmqService svc = new RabbitmqService();
		Boolean result = svc.isActive();
		assertThat(result, is(false));
	}

	@Test
	void testput() throws IOException, TimeoutException, NoSuchAlgorithmException {
		RabbitmqService svc = new RabbitmqService() {
			public Connection getConnection() {
				return conn;
			}
		};
		String result = svc.put();
		assertThat(result, containsString("Set id: "));
		assertThat(result, containsString(", msg:Hello k8s-3tier-webapp!"));
	}

	@Test
	void testgetWithData() throws IOException, TimeoutException, NoSuchAlgorithmException {
		RabbitmqService svc = new RabbitmqService() {
			public Connection getConnection() {
				return conn;
			}
		};
		svc.get();
		svc.put();
		String result = svc.get();
		assertThat(result, containsString("Get id: "));
		assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
	}

	@Test
	void testgetWithNoData() throws IOException, TimeoutException {
		RabbitmqService svc = new RabbitmqService() {
			public Connection getConnection() {
				return conn;
			}
		};
		String result = svc.get();
		assertThat(result, is("No Data"));
	}

	@Test
	void testpublish() throws IOException, TimeoutException, NoSuchAlgorithmException {
		RabbitmqService svc = new RabbitmqService() {
			public Connection getConnection() {
				return conn;
			}
		};
		String result = svc.publish();
		assertThat(result, containsString("Publish id: "));
		assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
	}
}
