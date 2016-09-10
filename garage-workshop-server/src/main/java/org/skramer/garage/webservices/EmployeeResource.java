package org.skramer.garage.webservices;

import org.skramer.garage.domain.Employee;
import org.skramer.garage.domain.Garage;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;

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
  public void addTool(Employee employee) {
    garage.addEmployee(employee);
  }

  @GET
  public List<Employee> getForResourceIdentifier(@DefaultValue("ANY") @QueryParam("type") String type,
                                                 @DefaultValue("ANY") @QueryParam("model") String model,
                                                 @DefaultValue("ANY") @QueryParam("brand") String brand) {

    return garage.findEmployeeFor(new ResourceIdentifier(GarageTool.CarType.valueOf(type),
                                                         GarageTool.CarBrand.valueOf(brand),
                                                         GarageTool.CarModel.valueOf(model)));
  }

  @DELETE
  public void removeTool(@QueryParam("employeeId") long employeeId) {
    garage.removeEmployee(employeeId);
  }

}
