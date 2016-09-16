package org.skramer.garage.webservices;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.jaxrs.SupportJaxRs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
  public void validEmployeeIsPersisted() {
    Employee employee = new Employee("john", "doe", ANY_RESOURCE_IDENTIFIER);

    employeeResource.addEmployee(employee);

    Mockito.verify(entityManagerMock).persist(employee);
  }
}
