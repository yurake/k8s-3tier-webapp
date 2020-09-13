package webapp.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import webapp.tier.cache.RedisService;
import webapp.tier.cache.memcached.GetMemcached;
import webapp.tier.cache.memcached.SetMemcached;
import webapp.tier.cache.redis.PublishRedis;
import webapp.tier.cache.redis.SetRedis;
import webapp.tier.db.MysqlService;
import webapp.tier.mq.RabbitmqService;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

	private MockMvc mockMvc;

	@Mock
	MysqlService mysqlsvc;

	@Mock
	RabbitmqService rabbitmqsvc;

	@Mock
	GetMemcached getmemcache;

	@Mock
	SetMemcached setmemcache;

	@Mock
	RedisService getrediscache;

	@Mock
	SetRedis setrediscache;

	@Mock
	PublishRedis publishrediscache;

	@InjectMocks
	ApplicationController appcont;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(appcont).build();
	}

	@Test
	public void testIndex() {
		try {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testInsertDb() {
		try {
			mockMvc.perform(get("/InsertMysql"))
					.andExpect(status().isOk())
					.andExpect(view().name("insertmysql"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSelectMysql() {
		try {
			mockMvc.perform(get("/SelectMysql"))
					.andExpect(status().isOk())
					.andExpect(view().name("selectmysql"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testDeleteMysql() {
		try {
			mockMvc.perform(get("/DeleteMysql"))
					.andExpect(status().isOk())
					.andExpect(view().name("deletemysql"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetMq() {
		try {
			mockMvc.perform(get("/GetRabbitmq"))
					.andExpect(status().isOk())
					.andExpect(view().name("getrabbitmq"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutMq() {
		try {
			mockMvc.perform(get("/PutRabbitmq"))
					.andExpect(status().isOk())
					.andExpect(view().name("putrabbitmq"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutMqConsumer() {
		try {
			mockMvc.perform(get("/PutRabbitmqConsumer"))
					.andExpect(status().isOk())
					.andExpect(view().name("putrabbitmqconsumer"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetMemcached() {
		try {
			mockMvc.perform(get("/GetMemcached"))
					.andExpect(status().isOk())
					.andExpect(view().name("getmemcached"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSetMemcached() {
		try {
			mockMvc.perform(get("/SetMemcached"))
					.andExpect(status().isOk())
					.andExpect(view().name("setmemcached"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetRedis() {
		try {
			mockMvc.perform(get("/GetRedis"))
					.andExpect(status().isOk())
					.andExpect(view().name("getredis"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSetRedis() {
		try {
			mockMvc.perform(get("/SetRedis"))
					.andExpect(status().isOk())
					.andExpect(view().name("setredis"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPublishRedis() {
		try {
			mockMvc.perform(get("/PublishRedis"))
					.andExpect(status().isOk())
					.andExpect(view().name("publishredis"))
					.andExpect(model().hasNoErrors());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
