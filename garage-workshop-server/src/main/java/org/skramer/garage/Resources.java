package org.skramer.garage;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by skramer on 9/10/16.
 * Resources exposed using resource producer pattern
 */
public class Resources {

  // Expose an entity manager using the resource producer pattern
  @PersistenceContext
  @Produces
  private EntityManager em;

}
