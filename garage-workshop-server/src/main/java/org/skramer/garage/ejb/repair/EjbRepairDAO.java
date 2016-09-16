package org.skramer.garage.ejb.repair;

import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.EmployeeFactory;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.Repair;
import org.skramer.garage.webservices.RepairDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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

  @Override
  public Repair addRepair(Repair repair) {
    entityManager.persist(repair);
    return repair;
  }

  @Override
  public void removeRepair(long repairId) {
    entityManager.remove(entityManager.find(Repair.class, repairId));
  }

  @Override
  public Repair getForId(long repairId) {
    return entityManager.find(Repair.class, repairId);
  }

  @Override
  public Repair fromDTO(RepairDTO repairDTO) {
    final List<Employee> employees = employeeFactory.getForIds(repairDTO.assignedEmployeesIds);

    final List<GarageTool> garageTools = repairDTO.assignedToolsIds.stream().map(
        it -> entityManager.find(GarageTool.class, it))
                                                                   .collect(Collectors.toList());
    return new Repair(employees, garageTools, repairDTO.expectedFinishDate, repairDTO.estimatedCost,
                      repairDTO.description, repairDTO.repairNotes);
  }
}
