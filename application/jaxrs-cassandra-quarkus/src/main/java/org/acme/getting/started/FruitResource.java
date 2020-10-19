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

  private static final String STORE_ID = "acme";

  @Inject FruitService fruitService;

  @GET
  public List<FruitDto> getAll() {
    return fruitService.get(STORE_ID).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @POST
  public void add(FruitDto fruit) {
    fruitService.save(convertFromDto(fruit));
  }

  private FruitDto convertToDto(Fruit fruit) {
    return new FruitDto(fruit.getName(), fruit.getDescription());
  }

  private Fruit convertFromDto(FruitDto fruitDto) {
    return new Fruit(STORE_ID, fruitDto.getName(), fruitDto.getDescription());
  }
}
