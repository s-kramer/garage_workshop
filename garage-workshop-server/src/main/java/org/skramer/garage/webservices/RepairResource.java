package org.skramer.garage.webservices;

import org.skramer.garage.domain.Garage;
import org.skramer.garage.domain.Repair;
import org.skramer.garage.ejb.repair.RepairDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Web service for garage repairs.
 */
@Path("/garage/repair")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class RepairResource {
  @Inject
  private Garage garage;

  @Inject
  private RepairDAO repairDAO;

  @POST
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Repair addRepair(RepairDTO repairDTO) {
    return garage.addRepair(repairDAO.fromDTO(repairDTO));
  }

  @GET
  public Repair getRepair(@QueryParam("repairId") long repairId) {
    return garage.findRepairById(repairId);
  }

  @DELETE
  public void removeTool(@QueryParam("repairId") long repairId) {
    garage.removeRepair(repairId);
  }
}

