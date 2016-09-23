package org.skramer.garage.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.CarCompatibilityBuilder;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.GarageTool_;
import org.skramer.garage.ejb.employee.EjbEmployeeDAO;
import org.skramer.garage.ejb.garageTool.EjbGarageToolDAO;
import org.skramer.garage.ejb.garageTool.GarageToolDAO;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by skramer on 9/21/16.
 * Integration tests for {@link EjbEmployeeDAO} class
 */
@RunWith(Arquillian.class)
public class GarageToolDAOIT extends DeploymentFactory {
  @Deployment
  public static WebArchive deployment() {
//    System.out.println(warArchive.toString(true));
    return DeploymentFactory.getBaseDeployment("GarageToolDAO-IT.war")
                            .addClasses(GarageTool.class, GarageTool_.class, GarageToolDAO.class,
                                        EjbGarageToolDAO.class,
                                        CarCompatibility.class, CarCompatibilityBuilder.class);
  }

  @Inject
  private EntityManager entityManager;

  @Inject
  private GarageToolDAO toolDAO;

  @Test
  public void aSingleToolCanBeFoundGivenCompatibility() {
    final CarCompatibility anyCarCompatibility = new CarCompatibilityBuilder().build();
    GarageTool tool = new GarageTool(anyCarCompatibility);
    toolDAO.addTool(tool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(anyCarCompatibility);

    assertThat(tools.size(), is(1));
  }
}
