package webapp.tier.service.client;

import javax.inject.Inject;
import javax.inject.Singleton;

import webapp.tier.bean.MsgBean;

@Singleton
public class MessageClientServiceMegBean {

	@Inject
	MessageClientService webappcltsvc;

	public MsgBean getMegBean(String type) {
		int grpcid = webappcltsvc.getId();
		String grpcMsg = webappcltsvc.getMsg();
		return new MsgBean(grpcid, grpcMsg, type);
	}
}
