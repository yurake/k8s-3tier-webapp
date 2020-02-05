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
import javax.naming.NamingException;

import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.util.CreateId;

@ApplicationScoped
public class PostgresService {

	private static final Logger LOG = Logger.getLogger(PostgresService.class.getSimpleName());
	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String url = ConfigProvider.getConfig().getValue("postgres.url", String.class);
	private static String sqlkey = ConfigProvider.getConfig().getValue("postgres.insert.msg.id", String.class);
	private static String sqlbody = ConfigProvider.getConfig().getValue("postgres.insert.msg.body", String.class);
	private static String insertsql = ConfigProvider.getConfig().getValue("postgres.insert.msg", String.class);
	private static String selectsql = ConfigProvider.getConfig().getValue("postgres.select.msg.all", String.class);
	private static String deletesql = ConfigProvider.getConfig().getValue("postgres.delete.msg.all", String.class);
	private Connection con = null;

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

	public String insert() throws SQLException, NamingException {
		Connection con = null;
		String sql  = null;
		String id = String.valueOf(CreateId.createid());

		try {
			sql = insertsql.replace(sqlkey, id).replace(sqlbody, message);

			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Insert SQL: " + sql);
			stmt.executeUpdate(sql);

		}  finally {
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

	public List<String> select() throws SQLException, NamingException {
		Connection con = null;
		List<String> allmsg = new ArrayList<>();

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Select SQL: " + selectsql);
			ResultSet rs = stmt.executeQuery(selectsql);

			while (rs.next()) {
				String fullmsg = "Selected Msg: id: " + rs.getString("id") + ", message: " + rs.getString("msg");
				LOG.info(fullmsg);
				allmsg.add(fullmsg);
			}

			if(allmsg.isEmpty()){
				allmsg.add("No Data");
			}

		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return allmsg;
	}

	public String delete() throws SQLException, NamingException {
		Connection con = null;

		try {
			con = getConnection();
			Statement stmt = con.createStatement();

			LOG.info("Delete SQL: " + deletesql);
			stmt.executeUpdate(deletesql);

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
