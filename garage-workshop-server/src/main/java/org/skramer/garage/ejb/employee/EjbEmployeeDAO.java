package org.skramer.garage.ejb.employee;

import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.ResourceIdentifier;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by skramer on 9/10/16.
 */
@Stateless
public class EjbEmployeeDAO implements EmployeeDAO {
  @Inject
  private EntityManager entityManager;

  // todo: return entity with ID after persisting
  @Override
  public Employee addEmployee(Employee employee) {
    entityManager.persist(employee);
    return employee;
  }

  @Override
  public void removeEmployee(long employee) {
    entityManager.remove(entityManager.find(Employee.class, employee));
  }

  @Override
  public List<Employee> getForResourceIdentifiers(List<ResourceIdentifier> resourceIdentifiers) {
    // todo: this provides exact matches only, the ANY value doesn't have it's special meaning
    final Query query = entityManager
        .createQuery("select e from Employee e where e.resourceIdentifier in :resourceIdentifiersList");
    query.setParameter("resourceIdentifiersList", resourceIdentifiers);
    return (List<Employee>) query.getResultList();
  }
}
