package org.skramer.garage.domain;

import org.skramer.garage.ejb.resource.AbstractResource;

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
public class GarageTool extends AbstractResource {

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
   * @param carCompatibility resource identifier specifying the requirements this tool is applicable to.
   */
  public GarageTool(final CarCompatibility carCompatibility) {
    super(carCompatibility);
  }

  public GarageTool(CarCompatibility carCompatibility, String toolDescription) {
    super(carCompatibility);
    this.toolDescription = toolDescription;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToolDescription() {
    return toolDescription;
  }

  public void setToolDescription(String toolDescription) {
    this.toolDescription = toolDescription;
  }

}
