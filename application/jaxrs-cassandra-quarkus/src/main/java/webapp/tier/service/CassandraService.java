package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import webapp.tier.bean.MsgBean;
import webapp.tier.dao.MsgDao;
import webapp.tier.entity.Msg;
import webapp.tier.exception.WebappServiceException;
import webapp.tier.util.CreateId;

@ApplicationScoped
public class CassandraService {

	@ConfigProperty(name = "common.message")
	String message;

	@Inject
	MsgDao dao;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public MsgBean insertMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		try {
			dao.update(new Msg(msgbean.getId(), msgbean.getMessage()));
			logger.log(Level.INFO, msgbean.getFullmsg());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Insert Errorr.", e);
			throw new WebappServiceException("Insert Error.", e);
		}
		return msgbean;
	}

	public List<MsgBean> selectMsg() {
		List<MsgBean> msglist = new ArrayList<>();
		try {
			for (Msg msg : dao.findAll()) {
				MsgBean msgbean = new MsgBean(msg.getId(), msg.getMsg(), "Select");
				msglist.add(msgbean);
				logger.log(Level.INFO, msgbean.getFullmsg());
			}
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Select"));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Select Errorr.", e);
			throw new WebappServiceException("Select Error.", e);
		}
		return msglist;
	}

	public String deleteMsg() {
		try {
			for (MsgBean msgbean : selectMsg()) {
				dao.deleteById(msgbean.getId());
				logger.log(Level.INFO, "Deleted: {0}", msgbean.getFullmsg());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Delete Errorr.", e);
			throw new WebappServiceException("Delete Error.", e);
		}
		return "Delete Msg Records";
	}

}
