package webapp.tier.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import webapp.tier.bean.MsgBean;

class MsgBeanUtilsTest {

	@Test
	void testFullmsg() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		beanutil.setFullmsg("Test");
		assertEquals("Test", beanutil.getFullmsg());
	}

	@Test
	void testFullmsgWithType() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		beanutil.setFullmsgWithType(bean, "A");
		assertEquals("A id: 1111, message: Test", beanutil.getFullmsg());
	}

	@Test
	void testAppendMessage() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		bean = beanutil.appendMessage(bean, "AppendTest");
		assertEquals("Test AppendTest", bean.getMessage());
	}

	@Test
	void testCreateBody() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		assertEquals("1111,Test", beanutil.createBody(bean, ","));
	}

	@Test
	void testSplitBody() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		MsgBean bean = beanutil.splitBody("1111,Test", ",");
		assertEquals(1111, bean.getId());
		assertEquals("Test", bean.getMessage());
	}

	@Test
	void testIdString() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		beanutil.setIdString("1111");
		assertEquals("1111", beanutil.getIdString());
	}

	@Test
	void testCheckMsgBeanUtils() {
		MsgBeanUtils beanutil = new MsgBeanUtils(1111, "Test");
		assertEquals(false, beanutil.checkMsgBeanUtils(beanutil));
	}

	@Test
	void testCheckMsgBeanUtilsIdNull() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		beanutil.setMessage("Test");
		assertEquals(0, beanutil.getId());
		assertEquals(true, beanutil.checkMsgBeanUtils(beanutil));
	}

	@Test
	void testCheckMsgBeanUtilsMsgNull() {
		MsgBeanUtils beanutil = new MsgBeanUtils();
		beanutil.setId(1111);
		assertEquals(null, beanutil.getMessage());
		assertEquals(true, beanutil.checkMsgBeanUtils(beanutil));
	}
}
