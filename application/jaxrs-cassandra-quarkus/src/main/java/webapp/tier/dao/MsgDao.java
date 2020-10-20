package webapp.tier.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import webapp.tier.entity.Msg;

@Dao
public interface MsgDao {

	@Update
	void update(Msg msgbean);

	@Select
	PagingIterable<Msg> selectAll();

}
