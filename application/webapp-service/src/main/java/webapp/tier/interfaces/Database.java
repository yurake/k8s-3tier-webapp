package webapp.tier.interfaces;

import java.sql.SQLException;
import java.util.List;

import webapp.tier.bean.MsgBean;

public interface Database {

	public MsgBean insertMsg() throws SQLException, Exception;

	public List<MsgBean> selectMsg() throws SQLException, Exception;

	public String deleteMsg() throws SQLException, Exception;
}
