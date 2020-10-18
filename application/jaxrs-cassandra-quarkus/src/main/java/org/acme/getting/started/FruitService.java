package org.acme.getting.started;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FruitService {

  private final FruitDao dao;

  @Inject
  public FruitService(FruitDao dao) {
    this.dao = dao;
  }

  public void save(Fruit fruit) {
    dao.update(fruit);
  }

  public List<Fruit> get(String id) {
    return dao.findById(id).all();
  }
}
