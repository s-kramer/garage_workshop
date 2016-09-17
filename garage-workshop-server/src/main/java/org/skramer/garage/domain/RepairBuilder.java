package org.skramer.garage.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RepairBuilder {
  private List<Employee> assignedEmployees;
  private List<GarageTool> assignedTools;
  private Date expectedFinishDate;
  private BigDecimal estimatedCost;
  private String description;
  private String repairNotes;

  public RepairBuilder(List<Employee> assignedEmployees, List<GarageTool> assignedTools) {
    this.assignedEmployees = assignedEmployees;
    this.assignedTools = assignedTools;
  }

  public RepairBuilder setExpectedFinishDate(Date expectedFinishDate) {
    this.expectedFinishDate = expectedFinishDate == null ? null : new Date(expectedFinishDate.getTime());
    return this;
  }

  public RepairBuilder setEstimatedCost(BigDecimal estimatedCost) {
    this.estimatedCost = estimatedCost;
    return this;
  }

  public RepairBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public RepairBuilder setRepairNotes(String repairNotes) {
    this.repairNotes = repairNotes;
    return this;
  }

  public Repair createRepair() {
    return new Repair(assignedEmployees, assignedTools, expectedFinishDate, estimatedCost, description, repairNotes);
  }
}