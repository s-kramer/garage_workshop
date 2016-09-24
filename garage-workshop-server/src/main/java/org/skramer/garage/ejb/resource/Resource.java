package org.skramer.garage.ejb.resource;

import org.skramer.garage.domain.CarCompatibility;

/**
 * Created by skramer on 9/24/16.
 * Represents resources used in Garage.
 */
public interface Resource {
  CarCompatibility getCarCompatibility();
}
