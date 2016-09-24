package org.skramer.garage.ejb.resource;

import org.skramer.garage.domain.CarCompatibility;

import javax.persistence.MappedSuperclass;

/**
 * Created by skramer on 9/24/16.
 * Skeleton implementation for Garage resources.
 */
@MappedSuperclass
public class AbstractResource implements Resource {
  /**
   * Identifies the tool by specifying the car type, brand and model this tool is applicable to.
   */
  private CarCompatibility carCompatibility;

  /**
   * Default constructor required by Entities and JAX-RS
   */
  protected AbstractResource() {
  }

  public AbstractResource(CarCompatibility carCompatibility) {
    this.carCompatibility = carCompatibility;
  }

  /**
   * Fetches the {@link CarCompatibility} for this object.
   *
   * @return {@link CarCompatibility} that specifies the requirements this tool is applicable to.
   */
  @Override
  public CarCompatibility getCarCompatibility() {
    return carCompatibility;
  }

  public void setCarCompatibility(CarCompatibility carCompatibility) {
    this.carCompatibility = carCompatibility;
  }

}
