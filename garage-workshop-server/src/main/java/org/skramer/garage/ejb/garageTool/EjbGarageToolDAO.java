package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.CarCompatibility_;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.GarageTool_;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.skramer.garage.domain.CarCompatibility.*;

/**
 * Created by skramer on 9/10/16.
 * Data access object for garage tool persistence service.
 */
@Stateless
public class EjbGarageToolDAO implements GarageToolDAO {
  @Inject
  private EntityManager entityManager;

  @Override
  public GarageTool addTool(GarageTool garageTool) {
    entityManager.persist(garageTool);
    return garageTool;
  }

  @Override
  public void removeTool(long toolId) {
    final GarageTool tool = entityManager.find(GarageTool.class, toolId);
    if (tool != null) {
      entityManager.remove(tool);
    }
  }

  @Override
  public List<GarageTool> getForCarCompatibility(CarCompatibility carCompatibility) {
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<GarageTool> query = cb.createQuery(GarageTool.class);
    final Root<GarageTool> root = query.from(GarageTool.class);

    query.select(root);
    query.where(buildPredicatesList(cb, root, carCompatibility).toArray(new Predicate[]{}));

    final TypedQuery<GarageTool> typedQuery = entityManager.createQuery(query);

    return typedQuery.getResultList();
  }

  private List<Predicate> buildPredicatesList(CriteriaBuilder cb,
                                              Root<GarageTool> root, CarCompatibility carCompatibility) {
    List<Predicate> result = new ArrayList<>();

    buildEqualToOrIsGenericPredicate(cb, carCompatibility.getCarBrand(), CarBrand.GENERIC,
                                     root.get(GarageTool_.carCompatibility).get(CarCompatibility_.carBrand))
        .ifPresent(result::add);

    buildEqualToOrIsGenericPredicate(cb, carCompatibility.getCarType(), CarType.GENERIC,
                                     root.get(GarageTool_.carCompatibility).get(CarCompatibility_.carType))
        .ifPresent(result::add);

    buildEqualToOrIsGenericPredicate(cb, carCompatibility.getCarModel(), CarModel.GENERIC,
                                     root.get(GarageTool_.carCompatibility).get(CarCompatibility_.carModel))
        .ifPresent(result::add);

    return result;
  }

  private <T extends IsGenericPredicate> Optional<Predicate> buildEqualToOrIsGenericPredicate(CriteriaBuilder cb,
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

  @Override
  public List<GarageTool> getForIds(List<Long> garageToolIds) {
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<GarageTool> query = cb.createQuery(GarageTool.class);
    final Root<GarageTool> root = query.from(GarageTool.class);
    query.select(root);
    query.where(root.get(GarageTool_.id).in(garageToolIds));
    query.orderBy(cb.asc(root.get(GarageTool_.id)));

    final TypedQuery<GarageTool> typedQuery = entityManager.createQuery(query);
    return typedQuery.getResultList();
  }
}
