package org.skramer.garage.domain;

/**
 * The builder for resource identifier. Allows to create
 * {@link CarCompatibility} objects that don't specify all of
 * the properties of the car the resource is applicable to.
 */
class CarCompatibilityBuilder {
  private GarageTool.CarType carType = GarageTool.CarType.ANY;
  private GarageTool.CarBrand carBrand = GarageTool.CarBrand.ANY;
  private GarageTool.CarModel carModel = GarageTool.CarModel.ANY;

  public CarCompatibilityBuilder type(GarageTool.CarType carType) {
    this.carType = carType;
    return this;
  }

  public CarCompatibilityBuilder brand(GarageTool.CarBrand carBrand) {
    this.carBrand = carBrand;
    return this;
  }

  public CarCompatibilityBuilder model(GarageTool.CarModel carModel) {
    this.carModel = carModel;
    return this;
  }

  public CarCompatibility build() {
    return new CarCompatibility(carType, carBrand, carModel);
  }
}