package org.skramer.garage.ejb.garageTool;

import org.skramer.garage.domain.GarageTool;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by skramer on 9/17/16.
 */
@Stateless
public class ToolsFactory {
  @Inject
  GarageToolDAO garageToolDAO;

  public List<GarageTool> getForIds(List<Long> assignedToolsIds) {
    return garageToolDAO.getForIds(assignedToolsIds);
  }
}
