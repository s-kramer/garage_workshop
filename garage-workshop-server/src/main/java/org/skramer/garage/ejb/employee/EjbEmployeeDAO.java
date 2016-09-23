package org.skramer.garage.ejb.employee;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.Employee_;

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

  @Override
  public Employee addEmployee(Employee employee) {
    entityManager.persist(employee);
    return employee;
  }

  @Override
  public void removeEmployee(long employeeId) {
    final Employee entity = entityManager.find(Employee.class, employeeId);
    if (entity != null) {
      entityManager.remove(entity);
    }
  }

  @Override
  public List<Employee> getForCarCompatibility(List<CarCompatibility> carCompetencies) {
    // todo: this provides exact matches only, the GENERIC value doesn't have it's special meaning
    final Query query = entityManager
        .createQuery("select e from Employee e where e.carCompatibility in :carCompatibilitysList");
    query.setParameter("carCompatibilitysList", carCompetencies);
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
