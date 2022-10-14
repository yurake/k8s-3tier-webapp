package webapp.tier.bean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;

class MsgBeanTest {

	@Test
	void testMsgBean() {
		MsgBean bean = new MsgBean(1111, "Test");
		assertThat(bean.getId(), is(1111));
		assertThat(bean.getMessage(), is("Test"));
	}

	@Test
	void testMsgBeanWithTypeInt() {
		MsgBean bean = new MsgBean(1111, "Test", "TEST");
		assertThat(bean.getId(), is(1111));
		assertThat(bean.getMessage(), is("Test"));
		assertThat(bean.getFullmsg(), containsString("TEST"));
	}

	@Test
	void testMsgBeanWithTypeString() {
		MsgBean bean = new MsgBean("1111", "Test", "TEST");
		assertThat(bean.getId(), is(1111));
		assertThat(bean.getMessage(), is("Test"));
		assertThat(bean.getFullmsg(), containsString("TEST"));
	}
	
	@Test
	void testFullMsg() {
		MsgBean bean = new MsgBean(1111, "Test");
		bean.setFullmsg("TEST");
		assertThat(bean.getFullmsg(), is("TEST : id: 1111, message: Test"));
	}

	@Test
	void testId() {
		MsgBean bean = new MsgBean();
		bean.setId(1111);
		assertThat(bean.getId(), is(1111));
	}

	@Test
	void testMsg() {
		MsgBean bean = new MsgBean();
		bean.setMessage("Test");
		assertThat(bean.getMessage(), is("Test"));
	}
}
