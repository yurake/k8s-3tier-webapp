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

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

@ApplicationScoped
public class MysqlService {

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());
	private static String url = GetConfig.getResourceBundle("mysql.url");
	private static String instersql = GetConfig.getResourceBundle("mysql.sql");
	private static String sqlkey = GetConfig.getResourceBundle("mysql.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.body");
	private static String addonmsg = GetConfig.getResourceBundle("mysql.msg.quarkus");
	private static String sql = GetConfig.getResourceBundle("mysql.select.msg.all");
	private static String message = GetConfig.getResourceBundle("common.message");
	Connection con = null;

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}

	public boolean connectionStatus() {
		boolean status = false;
		try {
			con = getConnection();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String insertMysql(String[] receivedbody) {

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return sql;
	}

	public Map<String, String> selectMysql() throws SQLException {

		Map<String, String> returnmsg = new HashMap<>();

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Execute SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
