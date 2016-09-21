package org.skramer.garage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a garage tool resource.
 * Each tool is identified by resource identifier that specifies car properties
 * that this tool is appropriate for.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class GarageTool {
  /**
   * Identifies the tool by specifying the car type, brand and model this tool is applicable to.
   */
  private /*final*/ CarCompatibility carCompatibility;

  /**
   * Entity ID.
   */
  @Id
  @GeneratedValue
  private Long id;

  private String toolDescription;

  /**
   * Default constructor for Jax-rs.
   */
  protected GarageTool() {
  }

  /**
   * Constructs a garage tool.
   *
   * @param resourceId resource identifier specifying the requirements this tool is applicable to.
   */
  public GarageTool(final CarCompatibility resourceId) {
    carCompatibility = resourceId;
  }

  public GarageTool(CarCompatibility carCompatibility, String toolDescription) {
    this.carCompatibility = carCompatibility;
    this.toolDescription = toolDescription;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Fetches the {@link CarCompatibility} for this object.
   *
   * @return {@link CarCompatibility} that specifies the requirements this tool is applicable to.
   */
  public final CarCompatibility getCarCompatibility() {
    return carCompatibility;
  }

  public void setCarCompatibility(CarCompatibility carCompatibility) {
    this.carCompatibility = carCompatibility;
  }

  public String getToolDescription() {
    return toolDescription;
  }

  public void setToolDescription(String toolDescription) {
    this.toolDescription = toolDescription;
  }

  public enum CarType {
    ANY,
    COMBI,
    SEDAN
  }

  public enum CarBrand {
    ANY,
    OPEL
  }

  public enum CarModel {
    ANY,
    VECTRA
  }
}
