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
import webapp.tier.util.CreateId;
import webapp.tier.util.MsgBeanUtils;

@ApplicationScoped
public class PostgresService {

	@Inject
	@DataSource("postgres")
	AgroalDataSource ds;

	@ConfigProperty(name = "common.message")
	String message;

	@ConfigProperty(name = "postgres.insert.msg")
	String insertsql;

	@ConfigProperty(name = "postgres.select.msg")
	String selectsql;

	@ConfigProperty(name = "postgres.delete.msg")
	String deletesql = "postgres.delete.msg";

	@ConfigProperty(name = "postgres.id")
	String sqlkey;

	@ConfigProperty(name = "postgres.body")
	String sqlbody;

	private static final Logger LOG = Logger.getLogger(PostgresService.class.getSimpleName());

	public boolean connectionStatus() {
		boolean status = false;
		try (Connection con = ds.getConnection()) {
			status = true;
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Status Check Error.", e);
		}
		return status;
	}

	public String insertMsg() throws SQLException, NoSuchAlgorithmException {
		MsgBeanUtils msgbean = new MsgBeanUtils(CreateId.createid(), message);
		String sql = insertsql.replace(sqlkey, msgbean.getIdString()).replace(sqlbody, msgbean.getMessage());

		try (Connection con = ds.getConnection();
				Statement stmt = con.createStatement()) {
			LOG.log(Level.INFO, "Insert SQL: {0}", sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Insert Error.", e);
			throw new SQLException("Insert Error.");
		}
		msgbean.setFullmsgWithType(msgbean, "Insert");
		LOG.info(msgbean.getFullmsg());
		return msgbean.getFullmsg();
	}

	public List<String> selectMsg() throws SQLException {
		List<String> msglist = new ArrayList<>();

		try (Connection con = ds.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(selectsql)) {
			LOG.log(Level.INFO, "Select SQL: {0}", selectsql);
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
			LOG.log(Level.SEVERE, "Select Errorr.", e);
			throw new SQLException("Select Error.");
		}
		return msglist;
	}

	public String deleteMsg() throws SQLException {

		try (Connection con = ds.getConnection();
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
