package com.swapnil.derbyJDBCConnector.model;

public class Circle {
  private int id;
  private String name;

  public Circle(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
