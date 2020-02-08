package webapp.tier.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.NamingException;

import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.util.CreateId;

@ApplicationScoped
public class MysqlService {

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String url = ConfigProvider.getConfig().getValue("mysql.url", String.class);
	private static String instersql = ConfigProvider.getConfig().getValue("mysql.insert.msg", String.class);
	private static String selectsql = ConfigProvider.getConfig().getValue("mysql.select.msg.all", String.class);
	private static String deletesql = ConfigProvider.getConfig().getValue("mysql.delete.msg.all", String.class);
	private static String sqlkey = ConfigProvider.getConfig().getValue("mysql.id", String.class);
	private static String sqlbody = ConfigProvider.getConfig().getValue("mysql.body", String.class);
	private static String addonmsg = ConfigProvider.getConfig().getValue("mysql.msg.quarkus", String.class);
	Connection con = null;

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}

	private void closeConnection() throws SQLException {
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
		boolean status = false;
		try {
			con = getConnection();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public String insertMysql() throws SQLException {

		String id = String.valueOf(CreateId.createid());

		String sql = instersql;
		sql = sql.replace(sqlkey, id);
		sql = sql.replace(sqlbody, message);

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Execute SQL: " + sql);
			stmt.executeUpdate(sql);
			return sql;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException();
		} finally {
			closeConnection();
		}
	}

	public String insertMsg(String[] receivedbody) {

		String id = receivedbody[0];

		StringBuilder builder = new StringBuilder();
		builder.append(receivedbody[1]);
		builder.append(" ");
		builder.append(addonmsg);
		String message = builder.toString();

		String sql = instersql;
		sql = sql.replace(sqlkey, id);
		sql = sql.replace(sqlbody, message);

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Execute SQL: " + sql);
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sql;
	}

	public Map<String, String> selectMsg() throws SQLException {

		Map<String, String> returnmsg = new HashMap<>();

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Execute SQL: " + selectsql);
			ResultSet rs = stmt.executeQuery(selectsql);

			while (rs.next()) {
				String id = rs.getString("id");
				String msg = rs.getString("msg");
				returnmsg.put(id, msg);

				LOG.info("Selected Msg: id: " + id + ", message: " + msg);
			}
			return returnmsg;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException();
		} finally {
			closeConnection();
		}
	}

	public String deleteMsg() throws SQLException, NamingException {
		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Delete SQL: " + deletesql);
			stmt.executeUpdate(deletesql);

		} finally {
			closeConnection();
		}
		return "Deleted";
	}
}
