package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;

import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by skramer on 9/10/16.
 * In-memory implementation of GarageToolDAO interface.
 *
 * A {@link List} implementation is used to persist the elements.
 */
@Alternative
public class LocalGarageToolDAO implements GarageToolDAO {
  private List<GarageTool> garageTools = new ArrayList<>();

  @Override
  public void addTool(GarageTool garageTool) {
    garageTools.add(garageTool);
  }

  @Override
  public void removeTool(long toolId) {
    garageTools.removeIf(it -> it.getId().equals(toolId));
  }

  @Override
  public GarageTool getForResourceIdentifier(ResourceIdentifier resourceIdentifier) {
    return garageTools.stream().filter(it -> it.getResourceIdentifier().equals(resourceIdentifier)).findFirst().orElse(null);
  }

  @Override
  public List<ResourceIdentifier> getResourceIdentifiers() {
    return garageTools.stream().map(GarageTool::getResourceIdentifier).collect(Collectors.toList());
  }

  @Override
  public List<GarageTool> getForResourceIdentifiers(List<ResourceIdentifier> resourceIdentifiers) {
    return garageTools.stream().filter(it -> resourceIdentifiers.contains(it.getResourceIdentifier())).collect(
        Collectors.toList());
  }
}
