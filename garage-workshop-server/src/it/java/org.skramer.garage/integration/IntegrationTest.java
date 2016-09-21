package org.skramer.garage.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skramer.greeter.Greeter;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by skramer on 9/18/16.
 */
@RunWith(Arquillian.class)
public class IntegrationTest {
  @Inject
  private Greeter greeter;

  @Deployment
  public static JavaArchive deployment() {
    final JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class)
                                              .addClass(Greeter.class)
                                              .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    System.out.println(javaArchive);
    return javaArchive;
  }

  @Test
  public void sanityTest() {
    assertThat(greeter.createGreeting("World"), is(equalTo("Hello, World")));
    greeter.greet(System.out, "World");
  }
}
