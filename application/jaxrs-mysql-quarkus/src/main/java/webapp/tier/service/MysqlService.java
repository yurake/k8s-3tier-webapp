package webapp.tier.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.ConfigProvider;

import webapp.tier.bean.MsgBean;
import webapp.tier.config.DbConfig;
import webapp.tier.interfaces.Database;

@ApplicationScoped
public class MysqlService implements Database {

	private static String message = ConfigProvider.getConfig().getValue("common.message", String.class);
	private static String url = ConfigProvider.getConfig().getValue("mysql.url", String.class);
	private static String insertsql = ConfigProvider.getConfig().getValue("mysql.insert.msg", String.class);
	private static String selectsql = ConfigProvider.getConfig().getValue("mysql.select.msg.all", String.class);
	private static String deletesql = ConfigProvider.getConfig().getValue("mysql.delete.msg.all", String.class);
	private static String sqlkey = ConfigProvider.getConfig().getValue("mysql.id", String.class);
	private static String sqlbody = ConfigProvider.getConfig().getValue("mysql.body", String.class);
	private static String addonmsg = ConfigProvider.getConfig().getValue("mysql.msg.quarkus", String.class);
	private DbService dbsvc = new DbService();
	private DbConfig dbconfig;

	public MysqlService(){
		dbconfig = new DbConfig(message, url, insertsql, selectsql, deletesql, sqlkey, sqlbody);
	}

	public boolean connectionStatus() {
		return dbsvc.connectionStatus(dbconfig.getUrl());
	}

	@Override
	public String insertMsg() throws SQLException, NoSuchAlgorithmException {
		return dbsvc.insertMsg(dbconfig);
	}

	public String insertMsg(MsgBean bean) throws SQLException, NoSuchAlgorithmException {
		return dbsvc.insertMsg(dbconfig, bean, addonmsg);
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