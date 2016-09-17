package org.skramer.garage.webservices;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.jaxrs.SupportJaxRs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;
import org.skramer.garage.ejb.employee.EmployeeDAO;
import org.skramer.garage.ejb.garageTool.EjbGarageToolDAO;
import org.skramer.garage.ejb.garageTool.GarageToolDAO;
import org.skramer.garage.ejb.repair.RepairDAO;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by skramer on 9/17/16.
 * Tests for Garage tool REST web resource
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(EjbGarageToolDAO.class)
@SupportJaxRs
public class GarageToolResourceTest {
  private static final ResourceIdentifier ANY_RESOURCE_IDENTIFIER = new ResourceIdentifier(GarageTool.CarType.ANY,
                                                                                           GarageTool.CarBrand.ANY,
                                                                                           GarageTool.CarModel.ANY);
  private static final Long ANY_TOOL_ID = 5L;

  @Inject private GarageToolResource toolResource;

  @Inject
  private GarageToolDAO garageToolDAO;

  @Mock
  @Produces
  private EmployeeDAO employeeDAO;

  @Mock
  @Produces
  private EntityManager entityManagerMock;

  @Mock
  @Produces
  private RepairDAO repairDAO;

  @Test
  public void userAddingRequestPersistsUserInDatabase() {
    final GarageTool tool = new GarageTool(ANY_RESOURCE_IDENTIFIER);

    toolResource.addTool(tool);

    verify(entityManagerMock).persist(tool);
  }

  @Test
  public void userDeleteRequestRemovesUserFromDatabase() {
    final GarageTool tool = new GarageTool(ANY_RESOURCE_IDENTIFIER);
    tool.setId(ANY_TOOL_ID);
    when(entityManagerMock.find(any(), eq(ANY_TOOL_ID))).thenReturn(tool);

    toolResource.removeTool(ANY_TOOL_ID);

    verify(entityManagerMock).remove(tool);
  }

  @Test
  public void userDeleteRequestForInexistingUserDoesNothing() {
    when(entityManagerMock.find(any(), eq(ANY_TOOL_ID))).thenReturn(null);

    toolResource.removeTool(ANY_TOOL_ID);

    verify(entityManagerMock, never()).remove(any());
  }

}
