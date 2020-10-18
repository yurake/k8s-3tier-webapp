package org.acme.getting.started;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

  private static final String STORE_NAME = "acme";

  @Inject FruitService fruitService;

  @GET
  public List<FruitDto> list() {
    return fruitService.get(STORE_NAME).stream()
        .map(fruit -> new FruitDto(fruit.getName(), fruit.getDescription()))
        .collect(Collectors.toList());
  }

  @POST
  public void add(FruitDto fruit) {
    fruitService.save(covertFromDto(fruit));
  }

  private Fruit covertFromDto(FruitDto fruitDto) {
    return new Fruit(fruitDto.getName(), fruitDto.getDescription(), STORE_NAME);
  }
}
