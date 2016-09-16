package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.GarageTool;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by skramer on 9/16/16.
 */
@Stateless
public class ToolFactory {
  @Inject
  private GarageToolDAO garageToolDAO;

  public List<GarageTool> getForIds(List<Long> garageToolIds) {
    return garageToolDAO.getForIds(garageToolIds);
  }

}

