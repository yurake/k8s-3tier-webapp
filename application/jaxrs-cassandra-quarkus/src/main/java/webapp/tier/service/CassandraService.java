package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import webapp.tier.bean.MsgBean;
import webapp.tier.util.CreateId;

@ApplicationScoped
public class CassandraService {

	  @ConfigProperty(name = "common.message") String message;

	public MsgBean insertMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		return msgbean;
	}

	public List<MsgBean> selectMsg() throws NoSuchAlgorithmException {
		List<MsgBean> msglist = new ArrayList<>();
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Select");
		msglist.add(msgbean);
		return msglist;
	}

	public String deleteMsg() {
		return "Delete Msg Records";
	}

}
