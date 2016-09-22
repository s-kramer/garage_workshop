package org.skramer.garage.domain;

/**
 * The builder for resource identifier. Allows to create
 * {@link CarCompatibility} objects that don't specify all of
 * the properties of the car the resource is applicable to.
 */
public class CarCompatibilityBuilder {
  private CarCompatibility.CarType carType = CarCompatibility.CarType.ANY;
  private CarCompatibility.CarBrand carBrand = CarCompatibility.CarBrand.ANY;
  private CarCompatibility.CarModel carModel = CarCompatibility.CarModel.ANY;

  public CarCompatibilityBuilder type(CarCompatibility.CarType carType) {
    this.carType = carType;
    return this;
  }

  public CarCompatibilityBuilder brand(CarCompatibility.CarBrand carBrand) {
    this.carBrand = carBrand;
    return this;
  }

  public CarCompatibilityBuilder model(CarCompatibility.CarModel carModel) {
    this.carModel = carModel;
    return this;
  }

  public CarCompatibility build() {
    return new CarCompatibility(carType, carBrand, carModel);
  }
}