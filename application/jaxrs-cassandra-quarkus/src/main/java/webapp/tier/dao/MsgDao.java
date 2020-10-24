package webapp.tier.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

import webapp.tier.entity.Msg;

@Dao
public interface MsgDao {

	@Update
	Boolean update(Msg msg);

	@Select
	Msg findById(int id);

	@Select
	PagingIterable<Msg> findAll();

	@Delete(entityClass = Msg.class)
	Boolean deleteById(int id);

}
