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
	private static String instersql = GetConfig.getResourceBundle("mysql.insert.msg");
	private static String sqlkey = GetConfig.getResourceBundle("mysql.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.body");
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String sql = GetConfig.getResourceBundle("mysql.select.msg.all");
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

	public Map<String, String> selectMysql() throws SQLException {

//		Set<FullMessage> returnmsg = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
		Map<String, String> returnmsg = new HashMap<>();

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Execute SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString("id");
				String msg = rs.getString("msg");
//				returnmsg.add(new FullMessage(id, msg));
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
