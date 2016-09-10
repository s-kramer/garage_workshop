package org.skramer.garage.domain;

import javax.persistence.*;
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
  private /*final*/ ResourceIdentifier resourceIdentifier;

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
  public GarageTool(final ResourceIdentifier resourceId) {
    resourceIdentifier = resourceId;
  }

  public GarageTool(ResourceIdentifier resourceIdentifier, String toolDescription) {
    this.resourceIdentifier = resourceIdentifier;
    this.toolDescription = toolDescription;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Fetches the {@link ResourceIdentifier} for this object.
   *
   * @return {@link ResourceIdentifier} that specifies the requirements this tool is applicable to.
   */
  public final ResourceIdentifier getResourceIdentifier() {
    return resourceIdentifier;
  }

  public void setResourceIdentifier(ResourceIdentifier resourceIdentifier) {
    this.resourceIdentifier = resourceIdentifier;
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
