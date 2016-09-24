package org.skramer.garage.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
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
import javax.transaction.UserTransaction;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.skramer.garage.domain.CarCompatibility.*;

/**
 * Created by skramer on 9/21/16.
 * Integration tests for {@link EjbEmployeeDAO} class
 */
@RunWith(Arquillian.class)
public class GarageToolDAOIT extends DeploymentFactory {

  private static final CarCompatibility GENERIC_CAR_COMPATIBILITY = new CarCompatibilityBuilder().build();
  private static final CarCompatibility OPEL_VECTRA_SEDAN_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.OPEL).model(CarModel.VECTRA).type(CarType.SEDAN).build();
  private static final CarCompatibility OPEL_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.OPEL).build();
  private static final CarCompatibility OPEL_VECTRA_COMBI_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.OPEL).model(CarModel.VECTRA).type(CarType.COMBI).build();
  private static final CarCompatibility FIAT_PUNTO_HATCHBACK_COMPATIBILITY =
      new CarCompatibilityBuilder().brand(CarBrand.FIAT).model(CarModel.PUNTO).type(CarType.HATCHBACK).build();

  @Deployment
  public static WebArchive deployment() {
    return
        DeploymentFactory.getBaseDeployment("GarageToolDAO-IT.war")
                         .addClasses(GarageTool.class, GarageTool_.class, GarageToolDAO.class, EjbGarageToolDAO.class);
  }

  @Inject
  private EntityManager entityManager;

  @Inject
  private GarageToolDAO toolDAO;

  @Inject
  private UserTransaction utx;

  @Before
  public void preparePersistenceTest() throws Exception {
    clearData();
    startTransaction();
  }

  private void clearData() throws Exception {
    utx.begin();
    entityManager.joinTransaction();
    entityManager.createQuery("delete from GarageTool").executeUpdate();
    utx.commit();
  }

  private void startTransaction() throws Exception {
    utx.begin();
    entityManager.joinTransaction();
  }

  @After
  public void finishPersistenceTest() throws Exception {
    commitTransaction();
  }

  private void commitTransaction() throws Exception {
    utx.commit();
  }

  @Test
  public void genericCarCapabilityIsMatchedAgainstGenericCarRequest() {
    final CarCompatibility anyCarCompatibility = GENERIC_CAR_COMPATIBILITY;
    GarageTool tool = new GarageTool(anyCarCompatibility);
    toolDAO.addTool(tool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(anyCarCompatibility);

    assertThat(tools.size(), is(1));
  }

  @Test
  public void concreteToolIsMatchedAgainstGenericCarRequest() {
    GarageTool tool = new GarageTool(OPEL_VECTRA_COMBI_COMPATIBILITY);
    toolDAO.addTool(tool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(GENERIC_CAR_COMPATIBILITY);

    assertThat(tools.size(), is(1));
  }


  @Test
  public void genericToolIsMatchedAgainstConcreteCarRequest() {
    GarageTool tool = new GarageTool(GENERIC_CAR_COMPATIBILITY);
    toolDAO.addTool(tool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(OPEL_VECTRA_COMBI_COMPATIBILITY);

    assertThat(tools.size(), is(1));
  }

  @Test
  public void concreteToolIsMatchedAgainstConcreteRequest() {
    GarageTool combiTool = new GarageTool(OPEL_VECTRA_COMBI_COMPATIBILITY);
    GarageTool sedanTool = new GarageTool(OPEL_VECTRA_SEDAN_COMPATIBILITY);
    toolDAO.addTool(combiTool);
    toolDAO.addTool(sedanTool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(OPEL_VECTRA_COMBI_COMPATIBILITY);

    assertThat(tools.size(), is(1));
  }

  @Test
  public void allToolsMatchGenericRequest() {
    GarageTool combiTool = new GarageTool(OPEL_VECTRA_COMBI_COMPATIBILITY);
    GarageTool sedanTool = new GarageTool(OPEL_VECTRA_SEDAN_COMPATIBILITY);
    toolDAO.addTool(combiTool);
    toolDAO.addTool(sedanTool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(GENERIC_CAR_COMPATIBILITY);

    assertThat(tools.size(), is(2));
  }

  @Test
  public void allGenericToolsAreApplicableToConcreteCar() {
    GarageTool genericTool_1 = new GarageTool(GENERIC_CAR_COMPATIBILITY);
    GarageTool genericTool_2 = new GarageTool(GENERIC_CAR_COMPATIBILITY);
    GarageTool genericTool_3 = new GarageTool(GENERIC_CAR_COMPATIBILITY);
    toolDAO.addTool(genericTool_1);
    toolDAO.addTool(genericTool_2);
    toolDAO.addTool(genericTool_3);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(OPEL_VECTRA_COMBI_COMPATIBILITY);

    assertThat(tools.size(), is(3));
  }

  @Test
  public void singleConcreteRequirementFiltersApplicableTools() {
    GarageTool opelVectraCombiTool = new GarageTool(OPEL_VECTRA_COMBI_COMPATIBILITY);
    GarageTool opelVectraSedanTool = new GarageTool(OPEL_VECTRA_SEDAN_COMPATIBILITY);
    GarageTool fiatPuntoHatchbackTool = new GarageTool(FIAT_PUNTO_HATCHBACK_COMPATIBILITY);
    toolDAO.addTool(opelVectraCombiTool);
    toolDAO.addTool(opelVectraSedanTool);
    toolDAO.addTool(fiatPuntoHatchbackTool);

    final List<GarageTool> tools = toolDAO.getForCarCompatibility(OPEL_COMPATIBILITY);

    assertThat(tools.size(), is(2));
  }
}
