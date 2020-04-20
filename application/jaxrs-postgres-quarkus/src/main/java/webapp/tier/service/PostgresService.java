package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.config.DbConfig;
import webapp.tier.interfaces.Database;

@ApplicationScoped
public class PostgresService implements Database {

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String url = ConfigProvider.getConfig().getValue("postgres.url", String.class);
	private static String sqlkey = ConfigProvider.getConfig().getValue("postgres.insert.msg.id", String.class);
	private static String sqlbody = ConfigProvider.getConfig().getValue("postgres.insert.msg.body", String.class);
	private static String insertsql = ConfigProvider.getConfig().getValue("postgres.insert.msg", String.class);
	private static String selectsql = ConfigProvider.getConfig().getValue("postgres.select.msg.all", String.class);
	private static String deletesql = ConfigProvider.getConfig().getValue("postgres.delete.msg.all", String.class);
	private DbService dbsvc = new DbService();
	private DbConfig dbconfig;

	public PostgresService(){
		dbconfig = new DbConfig(message, url, insertsql, selectsql, deletesql, sqlkey, sqlbody);
	}

	public boolean connectionStatus() {
		return dbsvc.connectionStatus(dbconfig.getUrl());
	}

	@Override
	public String insertMsg() throws SQLException, NoSuchAlgorithmException {
		return dbsvc.insertMsg(dbconfig);
	}

	@Override
	public List<String> selectMsg() throws SQLException {
		return dbsvc.selectMsg(dbconfig);
	}

	@Override
	public String deleteMsg() throws SQLException {
		return dbsvc.deleteMsg(dbconfig);
	}
}