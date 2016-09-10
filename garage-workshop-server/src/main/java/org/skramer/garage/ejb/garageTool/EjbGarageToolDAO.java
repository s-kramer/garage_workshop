package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
    entityManager.remove(entityManager.find(GarageTool.class, toolId));
  }

  @Override
  public GarageTool getForResourceIdentifier(ResourceIdentifier resourceIdentifier) {
    Query query = entityManager.createQuery("select t from GarageTool t where t.resourceIdentifier = :resourceIdentifier");
    query.setParameter("resourceIdentifier", resourceIdentifier);
    return  (GarageTool) query.getSingleResult();
  }

  @Override
  public List<ResourceIdentifier> getResourceIdentifiers() {
    final Query query = entityManager.createQuery("select t.resourceIdentifier from GarageTool t");
    return (List<ResourceIdentifier>) query.getResultList();
  }

  @Override
  public List<GarageTool> getForResourceIdentifiers(List<ResourceIdentifier> resourceIdentifiers) {
    final Query query = entityManager.createQuery("select t from GarageTool t where t.resourceIdentifier in :resourceIdentifierList");
    query.setParameter("resourceIdentifierList", resourceIdentifiers);
    return (List<GarageTool>) query.getResultList();
  }
}
