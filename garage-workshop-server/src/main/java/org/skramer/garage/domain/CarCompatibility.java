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

  public static final CarCompatibility GENERIC_CAR_COMPATIBILITY = new CarCompatibilityBuilder().build();
  public static final CarCompatibility OPEL_VECTRA_SEDAN_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.OPEL).model(CarModel.VECTRA).type(CarType.SEDAN).build();
  public static final CarCompatibility OPEL_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.OPEL).build();
  public static final CarCompatibility OPEL_VECTRA_COMBI_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.OPEL).model(CarModel.VECTRA).type(CarType.COMBI).build();
  public static final CarCompatibility FIAT_PUNTO_HATCHBACK_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.FIAT).model(CarModel.PUNTO).type(CarType.HATCHBACK).build();

  private /*final*/ CarType carType;
  private /*final*/ CarBrand carBrand;
  private /*final*/ CarModel carModel;

  /**
   * Default constructor for Jax-rs.
   */
  protected CarCompatibility() {
  }

  /**
   * Constructs identifier for a resource that is associated with given carType, carBrand
   * and carModel.
   */
  public CarCompatibility(CarType carType,
                          CarBrand carBrand,
                          CarModel carModel) {
    checkIfAllRequirementsAreSet(carType, carBrand, carModel);
    this.carType = carType;
    this.carBrand = carBrand;
    this.carModel = carModel;
  }

  private void checkIfAllRequirementsAreSet(CarType carType,
                                            CarBrand carBrand,
                                            CarModel carModel) {
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

  public CarType getCarType() {
    return carType;
  }

  public CarBrand getCarBrand() {
    return carBrand;
  }

  public CarModel getCarModel() {
    return carModel;
  }

  public interface IsGenericPredicate {
    boolean IS_GENERIC = true;
    boolean IS_SPECIFIC = false;

    boolean isGeneric();
  }

  public enum CarType implements IsGenericPredicate {
    GENERIC(IS_GENERIC),
    COMBI,
    SEDAN,
    HATCHBACK;

    private boolean isGeneric;

    CarType(boolean isGeneric) {
      this.isGeneric = isGeneric;
    }

    CarType() {
      this(IS_SPECIFIC);
    }

    @Override
    public boolean isGeneric() {
      return isGeneric;
    }

  }

  public enum CarBrand implements IsGenericPredicate {
    GENERIC(IS_GENERIC),
    OPEL,
    FIAT;

    private boolean isGeneric;

    CarBrand(boolean isGeneric) {
      this.isGeneric = isGeneric;
    }

    CarBrand() {
      this(IS_SPECIFIC);
    }

    @Override
    public boolean isGeneric() {
      return isGeneric;
    }
  }

  public enum CarModel implements IsGenericPredicate {
    GENERIC(IS_GENERIC),
    VECTRA,
    PUNTO;

    private boolean isGeneric;

    CarModel(boolean isGeneric) {
      this.isGeneric = isGeneric;
    }

    CarModel() {
      this(IS_SPECIFIC);
    }

    @Override
    public boolean isGeneric() {
      return isGeneric;
    }

  }
}
