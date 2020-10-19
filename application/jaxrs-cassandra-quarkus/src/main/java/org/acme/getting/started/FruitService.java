package org.acme.getting.started;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FruitService {

  @Inject FruitDao dao;

  public void save(Fruit fruit) {
    dao.update(fruit);
  }

  public List<Fruit> get(String id) {
    return dao.findById(id).all();
  }
}
