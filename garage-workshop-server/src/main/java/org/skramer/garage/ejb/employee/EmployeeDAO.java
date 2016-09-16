package org.skramer.garage.ejb.employee;

import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.ResourceIdentifier;

import java.util.List;

/**
 * Created by skramer on 9/10/16.
 */
public interface EmployeeDAO {
  Employee addEmployee(Employee employee);

  void removeEmployee(long employee);

  List<Employee> getForResourceIdentifiers(List<ResourceIdentifier> resourceIdentifiers);

  List<Employee> getForIds(List<Long> employee_ids);
}
