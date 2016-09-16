package org.skramer.garage.ejb.employee;

import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.Employee_;
import org.skramer.garage.domain.ResourceIdentifier;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

  @Override
  public List<Employee> getForIds(List<Long> employee_ids) {
    final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    final CriteriaQuery<Employee> query = cb.createQuery(Employee.class);

    final Root<Employee> root = query.from(Employee.class);
    query.select(root);
    query.where(root.get(Employee_.employeeId).in(employee_ids));
    query.orderBy(cb.asc(root.get(Employee_.surname)), cb.asc(root.get(Employee_.name)));

    final TypedQuery<Employee> typedQuery = entityManager.createQuery(query);

    return typedQuery.getResultList();
  }
}
