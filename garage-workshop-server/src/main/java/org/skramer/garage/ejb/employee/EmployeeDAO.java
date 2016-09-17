package org.skramer.garage.ejb.employee;

import org.skramer.garage.domain.CarCompetency;
import org.skramer.garage.domain.Employee;

import java.util.List;

/**
 * Created by skramer on 9/10/16.
 */
public interface EmployeeDAO {
  Employee addEmployee(Employee employee);

  void removeEmployee(long employee);

  List<Employee> getForResourceIdentifiers(List<CarCompetency> carCompetencies);

  List<Employee> getForIds(List<Long> employee_ids);
}
