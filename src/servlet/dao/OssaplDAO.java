package servlet.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import servlet.dto.MsgDTO;

public class OssaplDAO {

    public List<MsgDTO> findAll() {
	Connection con = null;
	String title = null;
	String author = null;
	List<MsgDTO> msgsDTO = new ArrayList();

	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return msgsDTO;
    }
}
