package org.skramer.garage;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.skramer.garage.domain.Garage;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;
import org.skramer.garage.domain.ResourceIdentifierBuilder;
import org.skramer.garage.ejb.LocalGarageToolDAO;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.skramer.garage.domain.GarageTool.*;
import static org.skramer.garage.domain.GarageTool.CarBrand.OPEL;
import static org.skramer.garage.domain.GarageTool.CarModel.VECTRA;
import static org.skramer.garage.domain.GarageTool.CarType.COMBI;
import static org.skramer.garage.domain.GarageTool.CarType.SEDAN;

/**
 * Created by skramer on 8/15/16.
 * <p>
 * -tool for single requirement
 * -tool for multiple requirements
 * -tool for no requirements
 * -tool for not all requirements
 */
public class GarageTest {
  private Garage garage;

  @Before
  public void createGarage() {
    garage = new Garage();
    garage.setDAO(new LocalGarageToolDAO());
  }

  @Test
  public void sanityTest() {
    assertThat(true, is(true));
  }

  @Test
  public void toolForSingleRequirementCanBeFound() {
    GarageTool garageTool = new GarageTool(new ResourceIdentifier(SEDAN, OPEL, VECTRA));
    garage.addTool(garageTool);

    assertThat(garage.findToolFor(SEDAN, OPEL, VECTRA),
               is(Collections.singletonList(garageTool)));
  }

  @Test
  public void noToolCanBeFoundIfNoToolsAreAvailable() {
    assertThat(garage.findToolFor(SEDAN, OPEL, VECTRA),
               is(Collections.emptyList()));
  }

  @Test
  public void toolCannotBeFoundIfTheCriteriaDoesNotMatch() {
    final GarageTool garageTool = new GarageTool(
        new ResourceIdentifier(COMBI, OPEL, VECTRA));

    garage.addTool(garageTool);

    assertThat(garage.findToolFor(SEDAN, OPEL, VECTRA),
               is(Collections.emptyList()));
  }

  @Test
  public void toolCanBeMatchedByResourceId() {
    final GarageTool garageTool = new GarageTool(
        new ResourceIdentifier(SEDAN, OPEL, VECTRA));

    garage.addTool(garageTool);

    assertThat(garage.findToolFor(new ResourceIdentifier(SEDAN, OPEL, VECTRA)),
               is(Collections.singletonList(garageTool)));
  }

  @Test
  public void toolCanBeMatchedUsingOnlyASingleRequirement() {
    final GarageTool garageTool = new GarageTool(
        new ResourceIdentifier(SEDAN, OPEL, VECTRA));

    garage.addTool(garageTool);

    assertThat(garage.findToolFor(
        new ResourceIdentifierBuilder().type(SEDAN).build()),
               is(Collections.singletonList(garageTool)));
  }

  @Test
  public void toolCanBeMatchedUsingTwoRequirements() {
    final GarageTool garageTool = new GarageTool(
        new ResourceIdentifier(SEDAN, OPEL, VECTRA));

    garage.addTool(garageTool);

    assertThat(garage.findToolFor(
        new ResourceIdentifierBuilder().type(SEDAN).model(VECTRA).build()),
               is(Collections.singletonList(garageTool)));
  }

  @Test
  public void resourceIdCannotBeCreatedIfThereAreNoRequirementsConstructor() {
    try {
      new ResourceIdentifier(null, null, null);
      fail("Should have thrown ISE as all requirements are nulls");
    } catch (IllegalStateException ignored) {
      // success
    }
  }

  @Test
  public void resourceIdCannotBeCreatedIfAnyRequirementIsMissing() {
    try {
      new ResourceIdentifier(null, OPEL, VECTRA);
      fail("Should have thrown ISE as one of the  requirements is null");
    } catch (IllegalStateException ignored) {
      // success
    }
  }

  @Test
  public void multipleToolsCanMatchSingleRequirement() {
    final GarageTool firstGarageTool = new GarageTool(
        new ResourceIdentifier(SEDAN, OPEL, VECTRA));
    final GarageTool secondGarageTool = new GarageTool(
        new ResourceIdentifier(SEDAN, OPEL, VECTRA));

    garage.addTool(firstGarageTool);
    garage.addTool(secondGarageTool);

    final List<GarageTool> toolList = garage.findToolFor(
        new ResourceIdentifierBuilder().brand(OPEL).model(VECTRA).build());
    assertThat(toolList, is(ImmutableList.of(firstGarageTool, secondGarageTool)));
    assertThat(toolList.size(), is(2));
  }

  @Test
  public void resourceForAnyModelIsMatchedAgainstConcreteModel() {
    final GarageTool tool = new GarageTool(new ResourceIdentifier(CarType.ANY, OPEL, VECTRA));
    garage.addTool(tool);

    assertThat(garage.findToolFor(new ResourceIdentifier(SEDAN, OPEL, VECTRA)),
               is(Collections.singletonList(tool)));
  }

  @Test
  public void resourceForAnyBrandIsMatchedAgainstConcreteModel() {
    final GarageTool tool = new GarageTool(new ResourceIdentifier(COMBI, CarBrand.ANY, VECTRA));
    garage.addTool(tool);

    assertThat(garage.findToolFor(new ResourceIdentifier(COMBI, OPEL, VECTRA)),
               is(Collections.singletonList(tool)));
  }

  @Test
  public void resourceForAnyTypeIsMatchedAgainstConcreteModel() {
    final GarageTool tool = new GarageTool(new ResourceIdentifier(COMBI, OPEL, CarModel.ANY));
    garage.addTool(tool);

    assertThat(garage.findToolFor(new ResourceIdentifier(COMBI, OPEL, VECTRA)),
               is(Collections.singletonList(tool)));
  }

  @Test
  public void genericResourceIsMatchedAgainstAnyTool() {
    final GarageTool firstTool = new GarageTool(new ResourceIdentifier(COMBI, OPEL, VECTRA));
    final GarageTool secondTool = new GarageTool(new ResourceIdentifier(SEDAN, OPEL, VECTRA));

    garage.addTool(firstTool);
    garage.addTool(secondTool);

    assertThat(garage.findToolFor(new ResourceIdentifier(CarType.ANY, CarBrand.ANY, CarModel.ANY)),
               is(ImmutableList.of(firstTool, secondTool)));
  }
}
