package webapp.tier.db;

import java.security.NoSuchAlgorithmException;
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
import org.springframework.stereotype.Service;

import webapp.tier.util.CreateId;
import webapp.tier.util.GetConfig;

@Service
public class MysqlService {

	private static Logger logger = LoggerFactory.getLogger(MysqlService.class);
	private static String jndiname = GetConfig.getResourceBundle("mysql.jndi.name");
	private static String sqlkey = GetConfig.getResourceBundle("mysql.insert.msg.id");
	private static String sqlbody = GetConfig.getResourceBundle("mysql.insert.msg.body");
	private static String message = GetConfig.getResourceBundle("common.message");
	private static String insertsql = GetConfig.getResourceBundle("mysql.insert.msg");
	private static String selectsql = GetConfig.getResourceBundle("mysql.select.msg.all");
	private static String deletesql = GetConfig.getResourceBundle("mysql.delete.msg.all");
	private DataSource ds = null;

	public Connection getConnection() throws SQLException, NamingException {
		InitialContext ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(jndiname);
		return ds.getConnection();
	}

	public boolean connectionStatus() {
		boolean status = false;
		try (Connection con = getConnection()) {
			status = true;
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		return status;
	}

	public String insert() throws SQLException, NamingException, NoSuchAlgorithmException {
		String sql = null;
		String id = String.valueOf(CreateId.createid());

		try (Connection con = getConnection();
				Statement stmt = con.createStatement()) {
			sql = insertsql.replace(sqlkey, id).replace(sqlbody, message);
			logger.info("Execute SQL: " + sql);
			stmt.executeUpdate(sql);
		}
		return sql;
	}

	public List<String> select() throws SQLException, NamingException {
		List<String> allmsg = new ArrayList<>();

		try (Connection con = getConnection();
				Statement stmt = con.createStatement()) {
			logger.info("Execute SQL: " + selectsql);
			ResultSet rs = stmt.executeQuery(selectsql);
			while (rs.next()) {
				String fullmsg = "Selected Msg: id: " + rs.getString("id") + ", message: " + rs.getString("msg");
				logger.info(fullmsg);
				allmsg.add(fullmsg);
			}
			if (allmsg.isEmpty()) {
				allmsg.add("No Data");
			}
		}
		return allmsg;
	}

	public String delete() throws SQLException, NamingException {
		try (Connection con = getConnection();
				Statement stmt = con.createStatement()) {
			logger.info("Delete SQL: " + deletesql);
			stmt.executeUpdate(deletesql);
		}
		return "Deleted";
	}
}
