package org.skramer.garage.domain;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Represents the garage resource identifier. The identifier should be associated
 * with a garage tool and specify the properties of the car it's applicable to.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class CarCompatibility implements Serializable {

  private /*final*/ GarageTool.CarType carType;
  private /*final*/ GarageTool.CarBrand carBrand;
  private /*final*/ GarageTool.CarModel carModel;

  /**
   * Default constructor for Jax-rs.
   */
  protected CarCompatibility() {
  }

  /**
   * Constructs identifier for a resource that is associated with given carType, carBrand
   * and carModel.
   */
  public CarCompatibility(GarageTool.CarType carType,
                          GarageTool.CarBrand carBrand,
                          GarageTool.CarModel carModel) {
    checkIfAllRequirementsAreSet(carType, carBrand, carModel);
    this.carType = carType;
    this.carBrand = carBrand;
    this.carModel = carModel;
  }

  private void checkIfAllRequirementsAreSet(GarageTool.CarType carType,
                                            GarageTool.CarBrand carBrand,
                                            GarageTool.CarModel carModel) {
    if (carType == null || carBrand == null || carModel == null) {
      throw new IllegalStateException("CarCompatibility can't be created "
                                          + "when any of the requirements is not set");
    }
  }

  @Override
  public final boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    CarCompatibility that = (CarCompatibility) other;

    return carType == that.carType && carBrand == that.carBrand && carModel == that.carModel;

  }

  @Override
  public final int hashCode() {
    int result = carType != null ? carType.hashCode() : 0;
    result = 31 * result + ( carBrand != null ? carBrand.hashCode() : 0 );
    result = 31 * result + ( carModel != null ? carModel.hashCode() : 0 );
    return result;
  }

  @Override
  public final String toString() {
    return "CarCompatibility{"
        + "carType=" + carType
        + ", carBrand=" + carBrand
        + ", carModel=" + carModel
        + '}';
  }

  public GarageTool.CarType getCarType() {
    return carType;
  }

  public GarageTool.CarBrand getCarBrand() {
    return carBrand;
  }

  public GarageTool.CarModel getCarModel() {
    return carModel;
  }
}
