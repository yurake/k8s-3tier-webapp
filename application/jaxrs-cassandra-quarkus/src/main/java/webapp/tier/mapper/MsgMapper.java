package webapp.tier.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

import webapp.tier.dao.MsgDao;

@Mapper
public interface MsgMapper {

	@DaoFactory
	MsgDao msgDao();

}
