package org.skramer.garage.domain;

import org.skramer.garage.ejb.resource.AbstractResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by skramer on 9/10/16.
 * Garage employee entity.
 */
@Entity
public class Employee extends AbstractResource {
  @Id
  @GeneratedValue
  private Long employeeId;

  private String name, surname;

  public Employee() {
  }

  public Employee(String name, String surname, CarCompatibility carCompatibility) {
    super(carCompatibility);
    this.name = name;
    this.surname = surname;
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
}
