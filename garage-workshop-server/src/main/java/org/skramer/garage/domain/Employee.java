package org.skramer.garage.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by skramer on 9/10/16.
 */
@Entity
public class Employee {
  @Id
  @GeneratedValue
  private Long employeeId;

  private String name, surname;

  private ResourceIdentifier resourceIdentifier;

  public Employee() {
  }

  public Employee(String name, String surname, ResourceIdentifier resourceIdentifier) {
    this.name = name;
    this.surname = surname;
    this.resourceIdentifier = resourceIdentifier;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public ResourceIdentifier getResourceIdentifier() {
    return resourceIdentifier;
  }

  // todo: rename ResourceIdentifier to ResourceSpecialization?
  public void setResourceIdentifier(ResourceIdentifier resourceIdentifier) {
    this.resourceIdentifier = resourceIdentifier;
  }
}
