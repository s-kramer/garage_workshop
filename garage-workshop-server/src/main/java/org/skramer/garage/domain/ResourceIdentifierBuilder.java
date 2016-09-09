package org.skramer.garage.domain;

/**
 * The builder for resource identifier. Allows to create
 * {@link ResourceIdentifier} objects that don't specify all of
 * the properties of the car the resource is applicable to.
 */
public class ResourceIdentifierBuilder {
  private GarageTool.CarType carType = GarageTool.CarType.ANY;
  private GarageTool.CarBrand carBrand = GarageTool.CarBrand.ANY;
  private GarageTool.CarModel carModel = GarageTool.CarModel.ANY;

  public ResourceIdentifierBuilder type(GarageTool.CarType carType) {
    this.carType = carType;
    return this;
  }

  public ResourceIdentifierBuilder brand(GarageTool.CarBrand carBrand) {
    this.carBrand = carBrand;
    return this;
  }

  public ResourceIdentifierBuilder model(GarageTool.CarModel carModel) {
    this.carModel = carModel;
    return this;
  }

  public ResourceIdentifier build() {
    return new ResourceIdentifier(carType, carBrand, carModel);
  }
}