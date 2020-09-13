package webapp.tier.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

public class MysqlService {

	private static Logger logger = LoggerFactory.getLogger(MysqlService.class);
	private static String sqlkey = GetConfig.getResourceBundle("mysql.insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.insert.msg.body");
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String jndiname = GetConfig.getResourceBundle("mysql.jndi.name");
	private static String sqlselect = GetConfig.getResourceBundle("mysql.select.msg.all");
	private static String sqldelete = GetConfig.getResourceBundle("mysql.delete.msg.all");
	private DataSource ds;
	private Connection con = null;

	public Connection getConnection() throws SQLException, NamingException {
		InitialContext ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(jndiname);
		return ds.getConnection();
	}

	public boolean connectionStatus() {
		boolean status = false;
		try {
			con = getConnection();
			status = true;
		} catch (SQLException | NamingException e) {
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

	public String insert() throws SQLException, NamingException {
		Connection con = null;
		String id = String.valueOf(CreateId.createid());
		String sql = GetConfig.getResourceBundle("mysql.insert.msg");

		sql = sql.replace(sqlkey, id);
		sql = sql.replace(sqlbody, message);

		MysqlService conmysql = new MysqlService();
		con = conmysql.getConnection();
		Statement stmt = con.createStatement();

		logger.info("Execute SQL: " + sql);
		stmt.executeUpdate(sql);

		if (con != null) {
			con.close();
		}
		return sql;
	}

	public List<String> select() throws SQLException, NamingException {
		List<String> allmsg = new ArrayList<>();
		MysqlService conmysql = new MysqlService();
		Connection con = conmysql.getConnection();
		Statement stmt = con.createStatement();

		logger.info("Execute SQL: " + sqlselect);
		ResultSet rs = stmt.executeQuery(sqlselect);

		while (rs.next()) {
			String fullmsg = "Selected Msg: id: " + rs.getString("id") + ", message: " + rs.getString("msg");
			logger.info(fullmsg);
			allmsg.add(fullmsg);
		}
		if (allmsg.isEmpty()) {
			allmsg.add("No Data");
		}

		if (con != null) {
			con.close();
		}
		return allmsg;
	}

	public String delete() throws SQLException, NamingException {
		Connection con = null;

		try {
			MysqlService conmysql = new MysqlService();
			con = conmysql.getConnection();
			Statement stmt = con.createStatement();

			logger.info("Delete SQL: " + sqldelete);
			stmt.executeUpdate(sqldelete);

		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "Deleted";
	}
}
