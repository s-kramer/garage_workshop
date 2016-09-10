package org.skramer.garage.domain;

import org.skramer.garage.ejb.GarageToolDAO;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a garage with tools.
 */
public class Garage {
  @Inject
  private GarageToolDAO toolsDAO;

  /**
   * Stores the given garageTool for later usage and retrieval.
   *
   * @param garageTool the garage tool for storage
   */
  public void addTool(final GarageTool garageTool) {
    toolsDAO.addTool(garageTool);
  }

  /**
   * Searches for a list of tools applicable for given requirements.
   *
   * @param carType  the requested car type
   * @param carBrand the requested car brand
   * @param carModel the requested car model
   * @return a list of tools that
   */
  public List<GarageTool> findToolFor(final GarageTool.CarType carType,
                                      final GarageTool.CarBrand carBrand,
                                      final GarageTool.CarModel carModel) {
    return findToolFor(new ResourceIdentifier(carType, carBrand, carModel));
  }

  /**
   * Fetches a list of garage tools applicable for the requirement.
   *
   * @param resourceIdentifier the identifier containing the requirements for the selected tools
   * @return a list of tools applicable to the requirements provided by resourceIdentifier
   */
  public List<GarageTool> findToolFor(final ResourceIdentifier resourceIdentifier) {
    // todo: replace with proper database query instead of two stage client query
    List<ResourceIdentifier> resourceIdentifiers = toolsDAO.getResourceIdentifiers();
    final List<ResourceIdentifier> matchingResourceIdentifiers = resourceIdentifiers
        .stream()
        .filter(it -> isIdentityOrEqualTo(resourceIdentifier.getCarBrand(),
                                          it.getCarBrand(),
                                          GarageTool.CarBrand.ANY))
        .filter(it -> isIdentityOrEqualTo(resourceIdentifier.getCarModel(),
                                          it.getCarModel(),
                                          GarageTool.CarModel.ANY))
        .filter(it -> isIdentityOrEqualTo(resourceIdentifier.getCarType(),
                                          it.getCarType(),
                                          GarageTool.CarType.ANY))
        .collect(Collectors.toList());

    return toolsDAO.getForResourceIdentifiers(matchingResourceIdentifiers);
  }

  /**
   * Returns true if any of the lhs and rhs is equal to identity element,
   * or if the arguments lhs and rhs are equal to each other.
   *
   * @param lhs      second argument for comparison
   * @param rhs      first argument for comparison
   * @param identity identity element for type T
   * @param <T>      generic type for the arguments
   * @return true is the first argument is null, or, otherwise, if it's equal to the second one.
   */
  private <T> boolean isIdentityOrEqualTo(final T lhs, final T rhs, final T identity) {
    return lhs == identity || rhs == identity || lhs.equals(rhs);
  }

  /**
   * This is really a workaround so that we don't need to use arquillian or specialization
   * todo: remove me
   * @param DAO the DAO to be used
   */
  void setDAO(GarageToolDAO DAO) {
    this.toolsDAO = DAO;
  }
}
