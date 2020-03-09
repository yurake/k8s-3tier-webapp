package webapp.tier.bean;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MsgBeanTest {

	@Test
	void testMsgBeanAll() {
		MsgBean bean = new MsgBean(1111, "Test");
		assertEquals(1111, bean.getId());
		assertEquals("Test", bean.getMessage());
	}

	@Test
	void testId() {
		MsgBean bean = new MsgBean();
		bean.setId(1111);
		assertEquals(1111, bean.getId());
	}

	@Test
	void testMsg() {
		MsgBean bean = new MsgBean();
		bean.setMessage("Test");
		assertEquals("Test", bean.getMessage());
	}
}
