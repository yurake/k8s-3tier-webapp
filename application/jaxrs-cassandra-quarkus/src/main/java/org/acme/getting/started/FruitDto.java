package org.acme.getting.started;

import java.util.Objects;

public class FruitDto {

  private String name;
  private String description;

  public FruitDto() {}

  public FruitDto(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FruitDto fruitDto = (FruitDto) o;

    if (!Objects.equals(name, fruitDto.name)) return false;
    return Objects.equals(description, fruitDto.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, name);
  }
}
