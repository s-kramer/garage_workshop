package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.CarCompatibility_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarCompatibilityPredicateFactory {

  public List<Predicate> buildEqualToOrIsGenericPredicatesList(CriteriaBuilder cb,
                                                               Path<CarCompatibility> carCompatibilityPath,
                                                               CarCompatibility carCompatibility) {
    List<Predicate> result = new ArrayList<>();

    buildEqualToOrIsGenericPredicate(cb, carCompatibility.getCarBrand(), CarCompatibility.CarBrand.GENERIC,
                                     carCompatibilityPath.get(CarCompatibility_.carBrand))
        .ifPresent(result::add);

    buildEqualToOrIsGenericPredicate(cb, carCompatibility.getCarType(), CarCompatibility.CarType.GENERIC,
                                     carCompatibilityPath.get(CarCompatibility_.carType))
        .ifPresent(result::add);

    buildEqualToOrIsGenericPredicate(cb, carCompatibility.getCarModel(), CarCompatibility.CarModel.GENERIC,
                                     carCompatibilityPath.get(CarCompatibility_.carModel))
        .ifPresent(result::add);

    return result;
  }

  private <T extends CarCompatibility.IsGenericPredicate> Optional<Predicate> buildEqualToOrIsGenericPredicate(CriteriaBuilder cb,
                                                                                                               T carCompatibilityElement,
                                                                                                               T genericCompatibilityElement,
                                                                                                               Path<T> fieldPath) {
    if (carCompatibilityElement.isGeneric()) {
      return Optional.empty();
    }

    final Predicate areElementsEqual = cb.equal(fieldPath, carCompatibilityElement);
    final Predicate isQueriedElementGeneric = cb.equal(fieldPath, genericCompatibilityElement);

    final Predicate result = cb.or(areElementsEqual, isQueriedElementGeneric);

    return Optional.of(result);
  }

}