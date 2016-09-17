package org.skramer.garage.domain;

/**
 * The builder for resource identifier. Allows to create
 * {@link CarCompetency} objects that don't specify all of
 * the properties of the car the resource is applicable to.
 */
class CarCompetencyBuilder {
  private GarageTool.CarType carType = GarageTool.CarType.ANY;
  private GarageTool.CarBrand carBrand = GarageTool.CarBrand.ANY;
  private GarageTool.CarModel carModel = GarageTool.CarModel.ANY;

  public CarCompetencyBuilder type(GarageTool.CarType carType) {
    this.carType = carType;
    return this;
  }

  public CarCompetencyBuilder brand(GarageTool.CarBrand carBrand) {
    this.carBrand = carBrand;
    return this;
  }

  public CarCompetencyBuilder model(GarageTool.CarModel carModel) {
    this.carModel = carModel;
    return this;
  }

  public CarCompetency build() {
    return new CarCompetency(carType, carBrand, carModel);
  }
}