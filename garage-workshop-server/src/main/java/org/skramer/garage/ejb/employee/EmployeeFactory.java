package org.skramer.garage.ejb.employee;

import org.skramer.garage.domain.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by skramer on 9/16/16.
 */
@Stateless
public class EmployeeFactory {
  @Inject
  private EmployeeDAO employeeDAO;

  public List<Employee> getForIds(List<Long> employee_ids) {
    return employeeDAO.getForIds(employee_ids);
  }

}
