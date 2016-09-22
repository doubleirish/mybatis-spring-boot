package com.example.model;

import java.util.Date;

/**
 * Created by credmond on 9/21/16.
 */
public class User {
  private int id;
  private String userName;
  private String firstName;
  private String lastName;
  private Date activeOn;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getActiveOn() {
    return activeOn;
  }

  public void setActiveOn(Date activeOn) {
    this.activeOn = activeOn;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", userName='" + userName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", activeOn=" + activeOn +
        '}';
  }
}
