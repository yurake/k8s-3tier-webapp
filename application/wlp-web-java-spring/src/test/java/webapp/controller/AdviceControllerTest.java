package webapp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	@InjectMocks
	AdviceController con;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(appcont).build();
	}

	@Test
	void testhandleservlet() throws Exception {
		mockMvc.perform(get("/api"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	void testHandle() throws Exception {
		Exception ex = mock(Exception.class);
		assertThat(con.handle(ex), is("404"));
	}

}
