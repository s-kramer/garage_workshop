package org.skramer.garage.webservices;

import org.skramer.garage.domain.Garage;
import org.skramer.garage.domain.GarageTool;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.skramer.garage.domain.GarageTool.*;

/**
 * Web service for garage tools.
 */
@Path("/garage/tool")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class GarageToolResource {
  @Inject
  private Garage garage;

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public GarageTool addTool(GarageTool garageTool) {
    return garage.addTool(garageTool);
  }

  @GET
  public List<GarageTool> getTools(@DefaultValue("ANY") @QueryParam("type") String type,
                                   @DefaultValue("ANY") @QueryParam("model") String model,
                                   @DefaultValue("ANY") @QueryParam("brand") String brand) {

    return garage.findToolFor(CarType.valueOf(type),
                              CarBrand.valueOf(brand),
                              CarModel.valueOf(model));
  }

  @DELETE
  public void removeTool(@QueryParam("toolId") long toolId) {
    garage.removeTool(toolId);
  }
}
