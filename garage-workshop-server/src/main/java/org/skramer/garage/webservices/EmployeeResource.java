package org.skramer.garage.webservices;

import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.Garage;
import org.skramer.garage.domain.GarageTool;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Web service for garage employees.
 */
@Path("/garage/employee")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class EmployeeResource {
  @Inject
  private Garage garage;

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Employee addEmployee(Employee employee) {
    return garage.addEmployee(employee);
  }

  @GET
  public List<Employee> getForCarCompatibility(@DefaultValue("ANY") @QueryParam("type") String type,
                                               @DefaultValue("ANY") @QueryParam("model") String model,
                                               @DefaultValue("ANY") @QueryParam("brand") String brand) {

    return garage.findEmployeeFor(new CarCompatibility(GarageTool.CarType.valueOf(type),
                                                       GarageTool.CarBrand.valueOf(brand),
                                                       GarageTool.CarModel.valueOf(model)));
  }

  @DELETE
  public void removeEmployee(@QueryParam("employeeId") long employeeId) {
    garage.removeEmployee(employeeId);
  }

}
