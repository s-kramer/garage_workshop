package org.skramer.garage.ejb;

import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;

import java.util.List;

/**
 * Created by skramer on 9/10/16.
 * Interface for garage tool data access implementations.
 */
public interface GarageToolDAO {
  void addTool(GarageTool garageTool);

  void removeTool(GarageTool garageTool);

  GarageTool getForResourceIdentifier(ResourceIdentifier resourceIdentifier);

  List<ResourceIdentifier> getResourceIdentifiers();

  List<GarageTool> getForResourceIdentifiers(List<ResourceIdentifier> resourceIdentifiers);
}
