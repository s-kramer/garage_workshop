package org.skramer.garage.integration;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.skramer.garage.Resources;
import org.skramer.garage.domain.CarCompatibility;
import org.skramer.garage.domain.CarCompatibilityBuilder;
import org.skramer.garage.domain.CarCompatibility_;
import org.skramer.garage.ejb.CarCompatibilityPredicateFactory;
import org.skramer.garage.ejb.resource.AbstractResource;
import org.skramer.garage.ejb.resource.AbstractResource_;
import org.skramer.garage.ejb.resource.Resource;
import org.skramer.garage.ejb.resource.ResourceDAO;

/**
 * Created by skramer on 9/22/16.
 * Utility class. Delivers resources common to all integration tests.
 */
class DeploymentFactory {
  static WebArchive getBaseDeployment(String archiveName) {
    return ShrinkWrap.create(WebArchive.class, archiveName)
                     .addClass(DeploymentFactory.class)
                     .addClass(Resources.class)
                     .addClasses(CarCompatibility.class, CarCompatibility_.class, CarCompatibilityBuilder.class)
                     .addClass(CarCompatibilityPredicateFactory.class)
                     .addClasses(Resource.class, AbstractResource.class, AbstractResource_.class, ResourceDAO.class)
                     .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                     .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                     .addAsWebInfResource("garage-workshop-integration-tests-ds.xml");
  }
}
