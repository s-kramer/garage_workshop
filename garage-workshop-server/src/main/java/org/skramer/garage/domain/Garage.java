package org.skramer.garage.domain;

import org.skramer.garage.ejb.employee.EmployeeDAO;
import org.skramer.garage.ejb.garageTool.GarageToolDAO;
import org.skramer.garage.ejb.repair.RepairDAO;

import javax.inject.Inject;
import java.util.List;

/**
 * Represents a garage with tools.
 */
public class Garage {
  @Inject
  private GarageToolDAO toolsDAO;

  @Inject
  private EmployeeDAO employeeDAO;

  @Inject
  private RepairDAO repairDAO;

  /**
   * Stores the given garageTool for later usage and retrieval.
   *
   * @param garageTool the garage tool for storage
   */
  public GarageTool addTool(final GarageTool garageTool) {
    return toolsDAO.addTool(garageTool);
  }

  /**
   * Searches for a list of tools applicable for given requirements.
   *
   * @param carType  the requested car type
   * @param carBrand the requested car brand
   * @param carModel the requested car model
   * @return a list of tools that
   */
  public List<GarageTool> findToolFor(final CarCompatibility.CarType carType,
                                      final CarCompatibility.CarBrand carBrand,
                                      final CarCompatibility.CarModel carModel) {
    return findToolFor(new CarCompatibility(carType, carBrand, carModel));
  }

  /**
   * Fetches a list of garage tools applicable for the requirement.
   *
   * @param carCompatibility the identifier containing the requirements for the selected tools
   * @return a list of tools applicable to the requirements provided by carCompatibility
   */
  private List<GarageTool> findToolFor(final CarCompatibility carCompatibility) {
    return toolsDAO.getForCarCompatibility(carCompatibility);
  }

  /**
   * Returns true if any of the lhs and rhs is equal to identity element,
   * or if the arguments lhs and rhs are equal to each other.
   *
   * @param lhs      second argument for comparison
   * @param rhs      first argument for comparison
   * @param identity identity element for type T
   * @param <T>      generic type for the arguments
   * @return true is the first argument is null, or, otherwise, if it's equal to the second one.
   */
  private <T> boolean isIdentityOrEqualTo(final T lhs, final T rhs, final T identity) {
    return lhs == identity || rhs == identity || lhs.equals(rhs);
  }

  public void removeTool(long toolId) {
    toolsDAO.removeTool(toolId);
  }

  public Employee addEmployee(Employee employee) {
    return employeeDAO.addEmployee(employee);
  }

  public void removeEmployee(long employeeId) {
    employeeDAO.removeEmployee(employeeId);
  }

  public List<Employee> findEmployeeFor(CarCompatibility carCompatibility) {
    return employeeDAO.getForCarCompatibility(carCompatibility);
  }

  public Repair addRepair(Repair repair) {
    return repairDAO.addRepair(repair);
  }

  public Repair findRepairById(long repairId) {
    return repairDAO.getForId(repairId);
  }

  public void removeRepair(long repairId) {
    repairDAO.removeRepair(repairId);
  }
}
