package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Database;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgUtils;

@ApplicationScoped
public class MysqlService implements Database {

	@Inject
	@DataSource("mysql")
	AgroalDataSource ds;

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "mysql.insert.msg")
	String insertsql;

	@ConfigProperty(name = "mysql.select.msg")
	String selectsql;

	@ConfigProperty(name = "mysql.delete.msg")
	String deletesql;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public boolean connectionStatus() {
		boolean status = false;
		try {
			ds.getConnection();
			status = true;
		} catch (SQLException | NullPointerException e) {
			logger.log(Level.SEVERE, "Status Check Error.", e);
		}
		return status;
	}

	@Override
	public MsgBean insertMsg() throws SQLException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		String sql = insertsql.replace("msgid", MsgUtils.intToString(msgbean.getId()))
				.replace("msgbody", msgbean.getMessage());

		try (Connection con = ds.getConnection();
				Statement stmt = con.createStatement()) {
			logger.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Insert Error.", e);
			throw new SQLException("Insert Error.", e);
		}
		logger.log(Level.INFO, msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	@CacheResult(cacheName = "mysql_select_msg")
	public List<MsgBean> selectMsg() throws SQLException {
		List<MsgBean> msglist = new ArrayList<>();

		try (Connection con = ds.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(selectsql)) {
			logger.log(Level.INFO, "Select SQL: {0}", selectsql);
			while (rs.next()) {
				MsgBean msgbean = new MsgBean(rs.getString("id"),
						rs.getString("msg"), "Select");
				logger.log(Level.INFO, msgbean.getFullmsg());
				msglist.add(msgbean);
			}
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Select"));
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Select Errorr.", e);
			throw new SQLException("Select Error.", e);
		}
		return msglist;
	}

	@CacheInvalidate(cacheName = "mysql_select_msg")
	public void invalidateCache() {
		logger.log(Level.INFO, "Invalidate Cache: mysql_select_msg");
	}

	@Override
	public String deleteMsg() throws SQLException {

		try (Connection con = ds.getConnection();
				Statement stmt = con.createStatement()) {
			logger.log(Level.INFO, "Delete SQL: {0}", deletesql);
			stmt.executeUpdate(deletesql);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Delete Errorr.", e);
			throw new SQLException("Delete Error.", e);
		}
		return "Delete Msg Records";
	}
}
