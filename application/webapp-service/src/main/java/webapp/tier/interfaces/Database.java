package webapp.tier.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Database {

	public String insertMsg() throws SQLException;
	public List<String> selectMsg() throws SQLException;
	public String deleteMsg() throws SQLException;
}
