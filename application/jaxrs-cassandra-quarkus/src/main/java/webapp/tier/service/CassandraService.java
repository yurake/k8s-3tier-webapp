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

	private static final Logger LOG = Logger.getLogger(CassandraService.class.getSimpleName());

	public MsgBean insertMsg() throws NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		try {
			dao.update(new Msg());
			LOG.log(Level.INFO, msgbean.getFullmsg());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Insert Errorr.", e);
			throw new WebappServiceException("Insert Error.");
		}
		return msgbean;
	}

	public List<MsgBean> selectMsg() {
		List<MsgBean> msglist = new ArrayList<>();
		try {
			dao.selectAll();
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Select"));
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Select Errorr.", e);
			throw new WebappServiceException("Select Error.");
		}
		return msglist;
	}

	public String deleteMsg() {
		try {
			dao.deleteById(new Msg());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Delete Errorr.", e);
			throw new WebappServiceException("Delete Error.");
		}
		return "Delete Msg Records";
	}

}
