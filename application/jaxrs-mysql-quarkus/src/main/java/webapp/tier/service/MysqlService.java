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

	@ConfigProperty(name = "mysql.id")
	String sqlkey;

	@ConfigProperty(name = "mysql.body")
	String sqlbody;

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());

	protected Connection getConnectionWrapper() throws SQLException {
		return ds.getConnection();

	}

	public boolean connectionStatus() {
		boolean status = false;
		try (Connection con = getConnectionWrapper()) {
			status = true;
		} catch (SQLException | NullPointerException e) {
			LOG.log(Level.SEVERE, "Status Check Error.", e);
		}
		return status;
	}

	@Override
	public MsgBean insertMsg() throws SQLException, NoSuchAlgorithmException {
		MsgBean msgbean = new MsgBean(CreateId.createid(), message, "Insert");
		String sql = insertsql.replace(sqlkey, MsgUtils.intToString(msgbean.getId())).replace(sqlbody, msgbean.getMessage());

		try (Connection con = getConnectionWrapper();
				Statement stmt = con.createStatement()) {
			LOG.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Insert Error.", e);
			throw new SQLException("Insert Error.");
		}
		LOG.info(msgbean.getFullmsg());
		return msgbean;
	}

	@Override
	public List<MsgBean> selectMsg() throws SQLException {
		List<MsgBean> msglist = new ArrayList<>();

		try (Connection con = getConnectionWrapper();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(selectsql)) {
			LOG.log(Level.INFO, "Select SQL: {0}", selectsql);
			while (rs.next()) {
				MsgBean msgbean = new MsgBean(MsgUtils.stringToInt(rs.getString("id")), rs.getString("msg"), "Select");
				LOG.info(msgbean.getFullmsg());
				msglist.add(msgbean);
			}
			if (msglist.isEmpty()) {
				msglist.add(new MsgBean(0, "No Data.", "Select"));
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Select Errorr.", e);
			throw new SQLException("Select Error.");
		}
		return msglist;
	}

	@Override
	public String deleteMsg() throws SQLException {

		try (Connection con = getConnectionWrapper();
				Statement stmt = con.createStatement()) {
			LOG.log(Level.INFO, "Delete SQL: {0}", deletesql);
			stmt.executeUpdate(deletesql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Delete Errorr.", e);
			throw new SQLException("Delete Error.");
		}
		return "Delete Msg Records";
	}
}
