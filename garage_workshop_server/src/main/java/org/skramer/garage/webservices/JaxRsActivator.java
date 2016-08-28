package org.skramer.garage.webservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Jax-rs activator class. Specifies the application path to /rest.
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
}
