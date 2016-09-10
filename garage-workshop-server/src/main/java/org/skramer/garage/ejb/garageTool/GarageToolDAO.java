package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;

import java.util.List;

/**
 * Created by skramer on 9/10/16.
 * Interface for garage tool data access implementations.
 */
public interface GarageToolDAO {
  GarageTool addTool(GarageTool garageTool);

  void removeTool(long garageTool);

  // todo: this should return a list
  GarageTool getForResourceIdentifier(ResourceIdentifier resourceIdentifier);

  List<ResourceIdentifier> getResourceIdentifiers();

  List<GarageTool> getForResourceIdentifiers(List<ResourceIdentifier> resourceIdentifiers);
}
