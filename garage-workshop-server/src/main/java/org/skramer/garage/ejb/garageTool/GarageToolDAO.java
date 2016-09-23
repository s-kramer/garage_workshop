package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.GarageTool;

import java.util.List;

/**
 * Created by skramer on 9/10/16.
 * Interface for garage tool data access implementations.
 */
public interface GarageToolDAO {
  GarageTool addTool(GarageTool garageTool);

  void removeTool(long garageTool);

  List<GarageTool> getForCarCompatibility(CarCompatibility carCompetencies);

  List<GarageTool> getForIds(List<Long> garageToolIds);
}
