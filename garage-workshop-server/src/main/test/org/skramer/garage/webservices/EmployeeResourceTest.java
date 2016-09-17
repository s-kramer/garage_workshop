package org.skramer.garage.webservices;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.jaxrs.SupportJaxRs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;
import org.skramer.garage.ejb.employee.EjbEmployeeDAO;
import org.skramer.garage.ejb.employee.EmployeeDAO;
import org.skramer.garage.ejb.garageTool.GarageToolDAO;
import org.skramer.garage.ejb.repair.RepairDAO;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.mockito.Mockito.*;

/**
 * Created by skramer on 9/16/16.
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(EjbEmployeeDAO.class)
@SupportJaxRs
public class EmployeeResourceTest {
  private static final ResourceIdentifier ANY_RESOURCE_IDENTIFIER = new ResourceIdentifier(GarageTool.CarType.ANY,
                                                                                           GarageTool.CarBrand.ANY,
                                                                                           GarageTool.CarModel.ANY);
  private static final Long ANY_EMPLOYEE_ID = 5L;

  @Inject private EmployeeResource employeeResource;

  @Inject private EmployeeDAO employeeDAO;

  @Mock
  @Produces
  private EntityManager entityManagerMock;

  @Mock
  @Produces
  private GarageToolDAO garageToolDAO;

  @Mock
  @Produces
  private RepairDAO repairDAO;

  @Test
  public void userAddingRequestPersistsUserInDatabase() {
    final Employee employee = new Employee("john", "doe", ANY_RESOURCE_IDENTIFIER);

    employeeResource.addEmployee(employee);

    verify(entityManagerMock).persist(employee);
  }

  @Test
  public void userDeleteRequestRemovesUserFromDatabase() {
    final Employee employee = new Employee("john", "doe", ANY_RESOURCE_IDENTIFIER);
    employee.setEmployeeId(ANY_EMPLOYEE_ID);
    when(entityManagerMock.find(any(), eq(ANY_EMPLOYEE_ID))).thenReturn(employee);

    employeeResource.removeEmployee(ANY_EMPLOYEE_ID);

    verify(entityManagerMock).remove(employee);
  }

  @Test
  public void userDeleteRequestForInexistingUserDoesNothing() {
    when(entityManagerMock.find(any(), eq(ANY_EMPLOYEE_ID))).thenReturn(null);

    employeeResource.removeEmployee(ANY_EMPLOYEE_ID);

    verify(entityManagerMock, never()).remove(any());

  }

}
