package webapp.tier.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.bean.MsgBean;
import webapp.tier.interfaces.Database;
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class MysqlService implements Database {

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String url = ConfigProvider.getConfig().getValue("mysql.url", String.class);
	private static String insertsql = ConfigProvider.getConfig().getValue("mysql.insert.msg", String.class);
	private static String selectsql = ConfigProvider.getConfig().getValue("mysql.select.msg.all", String.class);
	private static String deletesql = ConfigProvider.getConfig().getValue("mysql.delete.msg.all", String.class);
	private static String sqlkey = ConfigProvider.getConfig().getValue("mysql.id", String.class);
	private static String sqlbody = ConfigProvider.getConfig().getValue("mysql.body", String.class);
	private static String addonmsg = ConfigProvider.getConfig().getValue("mysql.msg.quarkus", String.class);

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}

	private void closeConnection(Connection con) throws SQLException {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new SQLException();
			}
		}
	}

	public boolean connectionStatus() {
		Connection con = null;
		boolean status = false;
		try {
			con = getConnection();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	@Override
	public String insertMsg() throws SQLException {
		Connection con = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String sql = insertsql.replace(sqlkey, msgbean.getIdString()).replace(sqlbody, msgbean.getMessage());

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Insert SQL: " + sql);
			stmt.executeUpdate(sql);


		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Insert Error.");
		} finally {
			closeConnection(con);
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public String insertMsg(MsgBean bean) throws SQLException {
		Connection con = null;
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), bean.getMessage());

		msgbean.appendMessage(msgbean, addonmsg);
		String sql = insertsql.replace(sqlkey, msgbean.getIdString()).replace(sqlbody, msgbean.getMessage());

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Insert SQL: " + sql);
			stmt.executeUpdate(sql);


		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Insert Error.");
		} finally {
			closeConnection(con);
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	@Override
	public List<String> selectMsg() throws SQLException {
		Connection con = null;
		List<String> msglist = new ArrayList<>();

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Select SQL: " + selectsql);
			ResultSet rs = stmt.executeQuery(selectsql);

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
			e.printStackTrace();
			throw new SQLException("Select Error.");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return msglist;
	}

	@Override
	public String deleteMsg() throws SQLException {
		Connection con = null;

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Delete SQL: " + deletesql);
			stmt.executeUpdate(deletesql);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Delete Error.");
		} finally {
			closeConnection(con);
		}
		return "Delete Msg Records";
	}
}
