package org.skramer.garage.ejb.resource;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.ejb.CarCompatibilityPredicateFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by skramer on 9/24/16.
 * Data access object for resources. Captures common methods for other Garage resources.
 */
public class ResourceDAO {
  @Inject
  private CarCompatibilityPredicateFactory carCompatibilityPredicateFactory;

  @Inject
  private EntityManager entityManager;

  public <T extends AbstractResource> List<T> getForCarCompatibility(Class<T> abstractResourceClazz,
                                                                     CarCompatibility carCompatibility) {
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<T> query = cb.createQuery(abstractResourceClazz);
    final Root<T> root = query.from(abstractResourceClazz);

    query.select(root);
    query.where(carCompatibilityPredicateFactory
                    .buildCarCompatibilityMatchPredicateList(cb, root.get(AbstractResource_.carCompatibility),
                                                             carCompatibility)
                    .toArray(new Predicate[]{}));

    final TypedQuery<T> typedQuery = entityManager.createQuery(query);

    return typedQuery.getResultList();
  }
}
