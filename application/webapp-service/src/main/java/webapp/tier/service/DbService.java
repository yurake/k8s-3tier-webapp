package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import webapp.tier.bean.MsgBean;
import webapp.tier.config.DbConfig;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class DbService {

	private static final Logger LOG = Logger.getLogger(DbService.class.getSimpleName());
	String insertErr = "Insert Error.";

	public boolean connectionStatus(String url) {
		boolean status = false;
		try (Connection con = DriverManager.getConnection(url)) {
			status = true;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Status Check Error.", e);
		}
		return status;
	}

	public String insertMsg(DbConfig dbconfig) throws SQLException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), dbconfig.getMessage());
		String sql = dbconfig.getInsertsql().replace(dbconfig.getSqlkey(), MsgUtils.intToString(msgbean.getId()))
				.replace(dbconfig.getSqlbody(), msgbean.getMessage());

		try (Connection con = DriverManager.getConnection(dbconfig.getUrl());
				Statement stmt = con.createStatement()) {
			LOG.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, insertErr, e);
			throw new SQLException();
		}
		msgbean.setFullmsg("Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public String insertMsg(DbConfig dbconfig, MsgBean bean, String aadonmsg)
			throws SQLException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), bean.getMessage());

		MsgUtils.appendMessage(msgbean, aadonmsg);
		String sql = dbconfig.getInsertsql().replace(dbconfig.getSqlkey(), MsgUtils.intToString(msgbean.getId()))
				.replace(dbconfig.getSqlbody(), msgbean.getMessage());

		try (Connection con = DriverManager.getConnection(dbconfig.getUrl());
				Statement stmt = con.createStatement()) {
			LOG.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, insertErr, e);
			throw new SQLException();
		}
		msgbean.setFullmsg("Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public List<String> selectMsg(DbConfig dbconfig) throws SQLException {
		List<String> msglist = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(dbconfig.getUrl());
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(dbconfig.getSelectsql())) {
			LOG.log(Level.INFO, "Select SQL: {0}", dbconfig.getSelectsql());

			while (rs.next()) {
				MsgBean msgbean = new MsgBean(rs.getString("id"), rs.getString("msg"), "Select");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean.getFullmsg());
			}

			if (msglist.isEmpty()) {
				msglist.add("No Data");
			}

		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Select Errorr.", e);
			throw new SQLException();
		}
		return msglist;
	}

	public String deleteMsg(DbConfig dbconfig) throws SQLException {
		try (Connection con = DriverManager.getConnection(dbconfig.getUrl());
				Statement stmt = con.createStatement()) {
			LOG.log(Level.INFO, "Delete SQL: {0}", dbconfig.getDeletesql());
			stmt.executeUpdate(dbconfig.getDeletesql());
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Delete Errorr.", e);
			throw new SQLException();
		}
		return "Delete Msg Records";
	}
}