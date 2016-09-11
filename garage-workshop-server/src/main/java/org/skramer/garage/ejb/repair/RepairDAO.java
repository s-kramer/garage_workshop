package org.skramer.garage.ejb.repair;

import org.skramer.garage.domain.Repair;
import org.skramer.garage.webservices.RepairDTO;

/**
 * Created by skramer on 9/11/16.
 */
public interface RepairDAO {
  Repair addRepair(Repair repair);

  void removeRepair(long repairId);

  Repair getForId(long repairId);

  Repair fromDTO(RepairDTO repairDTO);
}
