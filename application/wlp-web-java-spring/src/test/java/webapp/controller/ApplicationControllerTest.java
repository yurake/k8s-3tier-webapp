package webapp.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import webapp.tier.db.mysql.InsertMysql;
import webapp.tier.db.mysql.SelectMysql;
import webapp.tier.mq.rabbitmq.GetRabbitmq;

public class ApplicationControllerTest {

	private MockMvc mockMvc;

	@Mock
	private InsertMysql insmysql;
	@Mock
	private SelectMysql selmysql;

	@Mock
	private GetRabbitmq getrab;

	@InjectMocks
	private ApplicationController appcont;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(appcont).build();
	}

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().hasNoErrors());
	}

	@Test
	public void testInsertDbError() throws Exception {
		try {
			mockMvc.perform(get("/InsertMysql"));
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is(
					"Request processing failed; nested exception is javax.naming.NoInitialContextException: Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial"));
		}
	}

	@Test
	public void testSelectMysqlError() throws Exception {
		try {
			mockMvc.perform(get("/SelectMysql"));
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is(
					"Request processing failed; nested exception is javax.naming.NoInitialContextException: Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial"));
		}
	}

	@Test
	public void testDeleteMysqlError() throws Exception {
		try {
			mockMvc.perform(get("/DeleteMysql"));
			fail();
		} catch (Exception expected) {
			assertThat(expected.getMessage(), is(
					"Request processing failed; nested exception is javax.naming.NoInitialContextException: Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial"));
		}
	}

	@Test
	public void testGetMq() throws Exception {
		when(getrab.getMessageQueue()).thenReturn("Received id: 11111, msg: test");

		mockMvc.perform(get("/GetRabbitmq"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().hasNoErrors());
	}

	@Test
	public void testPutMq() {
		fail("まだ実装されていません");
	}

	@Test
	public void testPutMqBatch() {
		fail("まだ実装されていません");
	}

	@Test
	public void testGetMemcached() {
		fail("まだ実装されていません");
	}

	@Test
	public void testSetMemcached() {
		fail("まだ実装されていません");
	}

	@Test
	public void testGetRedis() {
		fail("まだ実装されていません");
	}

	@Test
	public void testSetRedis() {
		fail("まだ実装されていません");
	}

	@Test
	public void testPublishRedis() {
		fail("まだ実装されていません");
	}

}
