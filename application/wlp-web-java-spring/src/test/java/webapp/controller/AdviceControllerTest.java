package webapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import webapp.tier.mq.rabbitmq.GetRabbitmq;

class AdviceControllerTest {

	private MockMvc mockMvc;
	@Mock
	GetRabbitmq getrab;
	@InjectMocks
	ApplicationController appcont;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(appcont).build();
	}

	@Test
	void testHandle() throws Exception {
		mockMvc.perform(get("/api"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

}
