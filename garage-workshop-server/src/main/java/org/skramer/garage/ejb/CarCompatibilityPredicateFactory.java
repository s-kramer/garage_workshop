package org.skramer.garage.ejb;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.CarCompatibility_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.skramer.garage.domain.CarCompatibility.IsGenericPredicate;

public class CarCompatibilityPredicateFactory {

  public List<Predicate> buildCarCompatibilityMatchPredicateList(CriteriaBuilder cb,
                                                                 Path<CarCompatibility> carCompatibilityPath,
                                                                 CarCompatibility carCompatibility) {
    List<Predicate> result = new ArrayList<>();

    buildCarCompatibilityElementMatchPredicate(cb, carCompatibility.getCarBrand(),
                                               carCompatibilityPath.get(CarCompatibility_.carBrand))
        .ifPresent(result::add);

    buildCarCompatibilityElementMatchPredicate(cb, carCompatibility.getCarType(),
                                               carCompatibilityPath.get(CarCompatibility_.carType))
        .ifPresent(result::add);

    buildCarCompatibilityElementMatchPredicate(cb, carCompatibility.getCarModel(),
                                               carCompatibilityPath.get(CarCompatibility_.carModel))
        .ifPresent(result::add);

    return result;
  }

  private <T extends Enum<T> & IsGenericPredicate>
  Optional<Predicate> buildCarCompatibilityElementMatchPredicate(CriteriaBuilder cb,
                                                                 T carCompatibilityElement,
                                                                 Path<T> fieldPath) {
    if (isPredicateRequired(carCompatibilityElement)) {
      return buildEqualToOrIsGenericPredicate(cb, carCompatibilityElement, fieldPath);
    } else {
      return Optional.empty();
    }
  }

  private <T extends Enum<T> & IsGenericPredicate>
  Optional<Predicate> buildEqualToOrIsGenericPredicate(CriteriaBuilder cb, T carCompatibilityElement, Path<T> fieldPath) {

    final Predicate areElementsEqualPredicate = makeIsEqualToPredicate(cb, carCompatibilityElement, fieldPath);

    final Optional<T> genericCompatibilityElement = getCarCompatibilityElementGenericElement(carCompatibilityElement);

    if (genericCompatibilityElement.isPresent()) {
      final Predicate isQueriedElementGenericPredicate = makeIsEqualToPredicate(cb, genericCompatibilityElement.get(),
                                                                                fieldPath);
      final Predicate areElementsEqualOrIsElementGenericPredicate =
          cb.or(areElementsEqualPredicate, isQueriedElementGenericPredicate);

      return Optional.of(areElementsEqualOrIsElementGenericPredicate);
    }

    return Optional.of(areElementsEqualPredicate);
  }

  private <T extends IsGenericPredicate> boolean isPredicateRequired(T carCompatibilityElement) {
    return !carCompatibilityElement.isGeneric();
  }

  private <T extends Enum<T> & IsGenericPredicate>
  Optional<T> getCarCompatibilityElementGenericElement(T carCompatibilityElement) {
    return Arrays.stream(carCompatibilityElement.getDeclaringClass().getEnumConstants())
                 // Note: this line cannot be replaced with method reference
                 // as it will hit the JDK-8141508 bug
                 .filter(predicate -> predicate.isGeneric()).findFirst();
  }

  private <T extends Enum<T> & IsGenericPredicate> Predicate makeIsEqualToPredicate(CriteriaBuilder cb,
                                                                                    T carCompatibilityElement,
                                                                                    Path<T> fieldPath) {
    return cb.equal(fieldPath, carCompatibilityElement);
  }

}