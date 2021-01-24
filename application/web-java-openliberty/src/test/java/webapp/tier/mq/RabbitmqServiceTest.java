package webapp.tier.mq;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Objects;

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
	public void createRabbitmqMock() {
		conn = new MockConnectionFactory().newConnection();
	}

	@AfterEach
	public void closeRabbitmqMock() {
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
	void testput() {
		try {
			RabbitmqService svc = new RabbitmqService() {
				public Connection getConnection() {
					return conn;
				}
			};
			String result = svc.put();
			assertThat(result, containsString("Set id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testgetWithData() {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testgetWithNoData() {
		try {
			RabbitmqService svc = new RabbitmqService() {
				public Connection getConnection() {
					return conn;
				}
			};
			String result = svc.get();
			assertThat(result, is("No Data"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testpublish() {
		try {
			RabbitmqService svc = new RabbitmqService() {
				public Connection getConnection() {
					return conn;
				}
			};
			String result = svc.publish();
			assertThat(result, containsString("Publish id: "));
			assertThat(result, containsString(", msg: Hello k8s-3tier-webapp!"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
