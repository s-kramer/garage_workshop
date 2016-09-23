package org.skramer.garage.webservices;

import org.hamcrest.MatcherAssert;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.jaxrs.SupportJaxRs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.skramer.garage.domain.*;
import org.skramer.garage.ejb.employee.EmployeeDAO;
import org.skramer.garage.ejb.employee.EmployeeFactory;
import org.skramer.garage.ejb.garageTool.GarageToolDAO;
import org.skramer.garage.ejb.garageTool.ToolFactory;
import org.skramer.garage.ejb.repair.EjbRepairDAO;
import org.skramer.garage.ejb.repair.RepairDAO;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by skramer on 9/17/16.
 * Tests for Garage repairs REST web resource
 */
@RunWith(CdiRunner.class)
@SupportJaxRs
@AdditionalClasses(EjbRepairDAO.class)
public class RepairResourceTest {
  private static final CarCompatibility ANY_RESOURCE_IDENTIFIER = new CarCompatibility(CarCompatibility.CarType.GENERIC,
                                                                                       CarCompatibility.CarBrand.GENERIC,
                                                                                       CarCompatibility.CarModel.GENERIC);
  private static final Employee EMPLOYEE = new Employee("john", "doe", ANY_RESOURCE_IDENTIFIER);
  private static final GarageTool GARAGE_TOOL = new GarageTool(ANY_RESOURCE_IDENTIFIER);

  private static final Long ANY_REPAIR_ID = 5L;
  private static final Long ANY_EMPLOYEE_ID = 10L;
  private static final Long ANY_TOOL_ID = 15L;

  @Inject private RepairResource repairResource;

  @Inject private RepairDAO repairDAO;

  @Mock
  @Produces
  private GarageToolDAO garageToolDAO;

  @Mock
  @Produces
  private EmployeeDAO employeeDAO;

  @Mock
  @Produces
  private EntityManager entityManagerMock;

  @Mock
  @Produces
  private EmployeeFactory employeeFactory;

  @Mock
  @Produces
  private ToolFactory toolFactory;

  @Test
  public void repairAddingRequestPersistsUserInDatabase() {
    final List<Long> employeeIds = Collections.singletonList(ANY_EMPLOYEE_ID);
    final List<Long> toolIds = Collections.singletonList(ANY_TOOL_ID);
    final List<Employee> employees = Collections.singletonList(EMPLOYEE);
    final List<GarageTool> tools = Collections.singletonList(GARAGE_TOOL);
    when(employeeFactory.getForIds(employeeIds)).thenReturn(employees);
    when(toolFactory.getForIds(toolIds)).thenReturn(tools);

    final RepairDTO repairDTO = new RepairDTO();
    repairDTO.assignedEmployeesIds = employeeIds;
    repairDTO.assignedToolsIds = toolIds;
    repairResource.addRepair(repairDTO);

    ArgumentCaptor<Repair> captor = ArgumentCaptor.forClass(Repair.class);
    verify(entityManagerMock).persist(captor.capture());
    final Repair repair = captor.getValue();
    MatcherAssert.assertThat(repair.getAssignedEmployees(), is(employees));
    MatcherAssert.assertThat(repair.getAssignedTools(), is(tools));
  }

  @Test
  public void repairDeleteRequestRemovesUserFromDatabase() {
    Repair repair =
        new RepairBuilder(Collections.singletonList(EMPLOYEE), Collections.singletonList(GARAGE_TOOL)).createRepair();
    when(entityManagerMock.find(Repair.class, ANY_REPAIR_ID)).thenReturn(repair);

    repairResource.removeTool(ANY_REPAIR_ID);

    verify(entityManagerMock).remove(repair);
  }

  @Test
  public void repairDeleteRequestForInexistingUserDoesNothing() {
    when(entityManagerMock.find(Repair.class, ANY_REPAIR_ID)).thenReturn(null);

    repairResource.removeTool(ANY_REPAIR_ID);

    verify(entityManagerMock, never()).remove(any());
  }

}
