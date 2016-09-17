package org.skramer.garage.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by skramer on 9/11/16.
 * Workshop repair entity.
 */
@Entity
public class Repair {
  @Id
  @GeneratedValue
  private Long repairId;

  private String description;

  @Temporal(TemporalType.DATE)
  private Date expectedFinishDate;

  private BigDecimal estimatedCost;

  private String repairNotes;

  @OneToMany
  private List<Employee> assignedEmployees;

  @OneToMany
  private List<GarageTool> assignedTools;

  public Repair() {
  }

  public Repair(List<Employee> assignedEmployees, List<GarageTool> assignedTools, Date expectedFinishDate,
                BigDecimal estimatedCost, String description, String repairNotes) {
    this.description = description;
    setExpectedFinishDate(expectedFinishDate);
    this.estimatedCost = estimatedCost;
    this.repairNotes = repairNotes;
    this.assignedEmployees = assignedEmployees;
    this.assignedTools = assignedTools;
  }

  public Repair(List<Employee> assignedEmployees, List<GarageTool> assignedTools) {
    this.assignedEmployees = assignedEmployees;
    this.assignedTools = assignedTools;
  }

  public Long getRepairId() {
    return repairId;
  }

  public void setRepairId(Long repairId) {
    this.repairId = repairId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getExpectedFinishDate() {
    return expectedFinishDate == null ? null : new Date(expectedFinishDate.getTime());
  }

  public void setExpectedFinishDate(Date expectedFinishDate) {
    this.expectedFinishDate = expectedFinishDate == null ? null : new Date(expectedFinishDate.getTime());
  }

  public BigDecimal getEstimatedCost() {
    return estimatedCost;
  }

  public void setEstimatedCost(BigDecimal estimatedCost) {
    this.estimatedCost = estimatedCost;
  }

  public String getRepairNotes() {
    return repairNotes;
  }

  public void setRepairNotes(String repairNotes) {
    this.repairNotes = repairNotes;
  }

  public List<Employee> getAssignedEmployees() {
    return assignedEmployees;
  }

  public void setAssignedEmployees(List<Employee> assignedEmployees) {
    this.assignedEmployees = assignedEmployees;
  }

  public List<GarageTool> getAssignedTools() {
    return assignedTools;
  }

  public void setAssignedTools(List<GarageTool> assignedTools) {
    this.assignedTools = assignedTools;
  }

}
