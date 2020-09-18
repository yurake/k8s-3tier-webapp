package webapp.tier.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RedisSubscriberToMysqlTest {

	@Mock
	MysqlInsertService mysqlsvc;

	@InjectMocks
	RedisSubscriberToMysql sub;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testonMessage() {
		try {
			when(mysqlsvc.insert(ArgumentMatchers.any())).thenReturn("OK");
			sub.onMessage("TESTChannel", "TEST,Message");
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testonMessageError() {
		try {
			when(mysqlsvc.insert(ArgumentMatchers.any())).thenThrow(new SQLException());
			sub.onMessage("TESTChannel", "TEST,Message");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
