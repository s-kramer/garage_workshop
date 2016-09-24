package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.GarageTool_;
import org.skramer.garage.ejb.CarCompatibilityPredicateFactory;
import org.skramer.garage.ejb.resource.ResourceDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by skramer on 9/10/16.
 * Data access object for garage tool persistence service.
 */
@Stateless
public class EjbGarageToolDAO implements GarageToolDAO {
  @Inject private CarCompatibilityPredicateFactory carCompatibilityPredicateFactory;

  @Inject private EntityManager entityManager;

  @Inject private ResourceDAO resourceDAO;

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
    return resourceDAO.getForCarCompatibility(GarageTool.class, carCompatibility);
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
