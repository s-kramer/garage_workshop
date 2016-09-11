package org.skramer.garage.webservices;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by skramer on 9/11/16.
 */
public class RepairDTO {
  public Long repairId;

  public String description;

  public Date expectedFinishDate;

  public BigDecimal estimatedCost;

  public String repairNotes;

  public List<Long> assignedEmployeesIds;

  public List<Long> assignedToolsIds;
}
