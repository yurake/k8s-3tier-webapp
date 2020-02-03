package webapp.tier.resource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import webapp.tier.service.MysqlService;

@Alternative()
@Priority(1)
@ApplicationScoped
public class MockMysqlService extends MysqlService {

	@Override
	public String insertMysql() {
		return "inserted";
	}

	@Override
	public Map<String, String> selectMsg() {
		Map<String, String> returnmsg = new HashMap<>();
		returnmsg.put("11111", "selected");
		return returnmsg;
	}

}
