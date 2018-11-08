package com.swapnil.derbyJDBCConnector;

import com.swapnil.derbyJDBCConnector.model.Circle;
import com.swapnil.derbyJDBCConnector.repository.DAO;

public class Main {

  public static void main(String[] args) {
    Circle circle = new DAO().getCircle(1);
    System.out.println(circle.getName());
  }
}
