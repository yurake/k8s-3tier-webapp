package org.acme.getting.started;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.mapper.annotations.Update;

@Dao
public interface FruitDao {

	@Update
	void update(Fruit fruit);

	@Select
	PagingIterable<Fruit> findById(String storeId);
}
