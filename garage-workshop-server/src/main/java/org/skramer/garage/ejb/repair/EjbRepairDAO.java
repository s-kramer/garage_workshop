package org.skramer.garage.ejb.repair;

import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.Repair;
import org.skramer.garage.domain.RepairBuilder;
import org.skramer.garage.ejb.employee.EmployeeFactory;
import org.skramer.garage.ejb.garageTool.ToolFactory;
import org.skramer.garage.webservices.RepairDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by skramer on 9/11/16.
 * DAO object for garage repairs persistence.
 */
@Stateless
public class EjbRepairDAO implements RepairDAO {
  @Inject
  private EntityManager entityManager;

  @Inject
  private EmployeeFactory employeeFactory;

  @Inject
  private ToolFactory toolsFactory;

  @Override
  public Repair addRepair(Repair repair) {
    entityManager.persist(repair);
    return repair;
  }

  @Override
  public void removeRepair(long repairId) {
    final Repair repair = entityManager.find(Repair.class, repairId);
    if (repair != null) {
      entityManager.remove(repair);
    }
  }

  @Override
  public Repair getForId(long repairId) {
    return entityManager.find(Repair.class, repairId);
  }

  @Override
  public Repair fromDTO(RepairDTO repairDTO) {
    final List<Employee> employees = employeeFactory.getForIds(repairDTO.assignedEmployeesIds);

    final List<GarageTool> garageTools = toolsFactory.getForIds(repairDTO.assignedToolsIds);

    return new RepairBuilder(employees, garageTools).setExpectedFinishDate(repairDTO.expectedFinishDate)
                                                    .setEstimatedCost(repairDTO.estimatedCost)
                                                    .setDescription(repairDTO.description)
                                                    .setRepairNotes(repairDTO.repairNotes).createRepair();
  }
}
