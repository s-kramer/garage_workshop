package org.skramer.garage.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.CarCompatibilityBuilder;
import org.skramer.garage.domain.Employee;
import org.skramer.garage.ejb.employee.EjbEmployeeDAO;
import org.skramer.garage.ejb.employee.EmployeeDAO;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by skramer on 9/21/16.
 * Integration tests for {@link EjbEmployeeDAO} class
 */
@RunWith(Arquillian.class)
public class EmployeeDAOIT {
  @Deployment
  public static WebArchive deployment() {
    return DeploymentFactory.getBaseDeployment("employeeDAO-IT.war")
                            .addClasses(Employee.class, EmployeeDAO.class, EjbEmployeeDAO.class,
                                        CarCompatibility.class, CarCompatibilityBuilder.class);
  }

  @Inject
  private EntityManager entityManager;

  @Inject
  private EmployeeDAO employeeDAO;

  @Test
  public void sanityTest() {
    assertThat(true, is(true));
  }

  @Test
  public void employeeCanBePersisted() {
    Employee employee = new Employee("EmployeeName", "EmployeeSurname", new CarCompatibilityBuilder().build());
    Employee savedEmployee = employeeDAO.addEmployee(employee);

    assertThat(savedEmployee.getEmployeeId(), notNullValue());
  }
}
