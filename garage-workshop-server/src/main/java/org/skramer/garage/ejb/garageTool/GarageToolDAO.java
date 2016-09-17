package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.CarCompetency;
import org.skramer.garage.domain.GarageTool;

import java.util.List;

/**
 * Created by skramer on 9/10/16.
 * Interface for garage tool data access implementations.
 */
public interface GarageToolDAO {
  GarageTool addTool(GarageTool garageTool);

  void removeTool(long garageTool);

  List<CarCompetency> getResourceIdentifiers();

  List<GarageTool> getForResourceIdentifiers(List<CarCompetency> carCompetencies);

  List<GarageTool> getForIds(List<Long> garageToolIds);
}
