package webapp.tier.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import webapp.tier.bean.MsgBean;

class MsgUtilsTest {

	@Test
	void testAppendMessage() {
		MsgBean bean = new MsgBean(1111, "Test");
		bean = MsgUtils.appendMessage(bean, "AppendTest");
		assertEquals("Test AppendTest", bean.getMessage());
	}

	@Test
	void testCreateBody() {
		MsgUtils beanutil = new MsgUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		assertEquals("1111,Test", beanutil.createBody(bean, ","));
	}

	@Test
	void testSplitBody() {
		MsgUtils beanutil = new MsgUtils();
		MsgBean bean = beanutil.splitBody("1111,Test", ",");
		assertEquals(1111, bean.getId());
		assertEquals("Test", bean.getMessage());
	}

	@Test
	void testIdString() {
		assertEquals("1111", MsgUtils.intToString(1111));
	}

	@Test
	void testCheckMsgBeanUtils() {
		MsgBean bean = new MsgBean(1111, "Test");
		assertEquals(false, MsgUtils.checkMsgBeanUtils(bean));
	}

	@Test
	void testCheckMsgBeanUtilsIdNull() {
		MsgBean beanutil = new MsgBean();
		beanutil.setMessage("Test");
		assertEquals(0, beanutil.getId());
		assertEquals(true, MsgUtils.checkMsgBeanUtils(beanutil));
	}

	@Test
	void testCheckMsgBeanUtilsMsgNull() {
		MsgBean beanutil = new MsgBean();
		beanutil.setId(1111);
		assertEquals(null, beanutil.getMessage());
		assertEquals(true, MsgUtils.checkMsgBeanUtils(beanutil));
	}
}
