package org.acme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import org.acme.util.CreateId;
import org.acme.util.FullMessage;
import org.acme.util.GetConfig;

@ApplicationScoped
public class MysqlService {

	private static final Logger LOG = Logger.getLogger(MysqlService.class.getSimpleName());
	private static String url = GetConfig.getResourceBundle("mysql.url");
	private static String instersql = GetConfig.getResourceBundle("insert.msg");
	private static String sqlkey = GetConfig.getResourceBundle("mysql.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.body");
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String sql = GetConfig.getResourceBundle("select.msg.all");
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

	public String insertMysql() {

		String id = String.valueOf(CreateId.createid());

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

	public Set<FullMessage> selectMysql() {

		Set<FullMessage> returnmsg = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Execute SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString("id");
				String msg = rs.getString("msg");
				returnmsg.add(new FullMessage(id, msg));

				LOG.info("Selected Msg: id: " + id + ", message: " + msg);
			}

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
	return returnmsg;
	}
}
