package webapp.tier.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import webapp.tier.bean.MsgBean;

class MsgBeanUtilsTest {

	@Test
	void testFullmsg() {
		MsgBeanUtils extractor = new MsgBeanUtils();
		extractor.setFullmsg("Test");
		assertEquals("Test", extractor.getFullmsg());
	}

	@Test
	void testFullmsgWithType() {
		MsgBeanUtils extractor = new MsgBeanUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		extractor.setFullmsgWithType(bean, "A");
		assertEquals("A id: 1111, message: Test", extractor.getFullmsg());
	}

	@Test
	void testAppendMessage() {
		MsgBeanUtils extractor = new MsgBeanUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		bean = extractor.appendMessage(bean, "AppendTest");
		assertEquals("Test AppendTest", bean.getMessage());
	}

	@Test
	void testCreateBody() {
		MsgBeanUtils extractor = new MsgBeanUtils();
		MsgBean bean = new MsgBean(1111, "Test");
		assertEquals("1111,Test", extractor.createBody(bean, ","));
	}

	@Test
	void testSplitBody() {
		MsgBeanUtils extractor = new MsgBeanUtils();
		MsgBean bean = extractor.splitBody("1111,Test", ",");
		assertEquals(1111, bean.getId());
		assertEquals("Test", bean.getMessage());

	}
}
