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
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class DbService {

	private static final Logger LOG = Logger.getLogger(DbService.class.getSimpleName());

	private Connection getConnection(String url) throws SQLException {
		return DriverManager.getConnection(url);
	}

	public boolean connectionStatus(String url) {
		boolean status = false;
		try (Connection con = getConnection(url)) {
			status = true;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Status Check Error.", e);
		}
		return status;
	}

	public String insertMsg(DbConfig dbconfig) throws SQLException, NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), dbconfig.getMessage());
		String sql = dbconfig.getInsertsql().replace(dbconfig.getSqlkey(), msgbean.getIdString()).replace(dbconfig.getSqlbody(), msgbean.getMessage());

		try (Statement stmt = getConnection(dbconfig.getUrl()).createStatement()) {
			LOG.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Insert Error.", e);
			throw new SQLException("Insert Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public String insertMsg(DbConfig dbconfig, MsgBean bean, String aadonmsg) throws SQLException, NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), bean.getMessage());

		msgbean.appendMessage(msgbean, aadonmsg);
		String sql = dbconfig.getInsertsql().replace(dbconfig.getSqlkey(), msgbean.getIdString()).replace(dbconfig.getSqlbody(), msgbean.getMessage());

		try (Statement stmt = getConnection(dbconfig.getUrl()).createStatement()) {
			LOG.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Insert Errorr.", e);
			throw new SQLException("Insert Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public List<String> selectMsg(DbConfig dbconfig) throws SQLException {
		List<String> msglist = new ArrayList<>();

		try (Statement stmt = getConnection(dbconfig.getUrl()).createStatement();
				ResultSet rs = stmt.executeQuery(dbconfig.getSelectsql())) {
			LOG.log(Level.INFO, "Select SQL: {0}", dbconfig.getSelectsql());

			while (rs.next()) {
				MsgBeanUtils msgbean = new MsgBeanUtils();
				msgbean.setIdString(rs.getString("id"));
				msgbean.setMessage(rs.getString("msg"));
				msgbean.setFullmsgWithType(msgbean, "Select");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean.getFullmsg());
			}

			if (msglist.isEmpty()) {
				msglist.add("No Data");
			}

		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Select Errorr.", e);
			throw new SQLException("Select Error.");
		}
		return msglist;
	}

	public String deleteMsg(DbConfig dbconfig) throws SQLException {
		try (Statement stmt = getConnection(dbconfig.getUrl()).createStatement()) {
			LOG.log(Level.INFO, "Select SQL: {0}", dbconfig.getDeletesql());
			stmt.executeUpdate(dbconfig.getDeletesql());
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Delete Errorr.", e);
			throw new SQLException("Delete Error.");
		}
		return "Delete Msg Records";
	}
}