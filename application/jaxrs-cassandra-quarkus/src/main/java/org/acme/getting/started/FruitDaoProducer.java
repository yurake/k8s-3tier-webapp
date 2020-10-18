package org.acme.getting.started;

import com.datastax.oss.quarkus.runtime.api.session.QuarkusCqlSession;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class FruitDaoProducer {

  private final FruitDao fruitDao;

  @Inject
  public FruitDaoProducer(QuarkusCqlSession session) {
    // create a mapper
    FruitMapper mapper = new FruitMapperBuilder(session).build();
    // instantiate our Daos
    fruitDao = mapper.fruitDao();
  }

  @Produces
  @ApplicationScoped
  FruitDao produceFruitDao() {
    return fruitDao;
  }
}
