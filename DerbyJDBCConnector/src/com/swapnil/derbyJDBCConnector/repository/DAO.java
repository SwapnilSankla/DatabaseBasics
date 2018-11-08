package com.swapnil.derbyJDBCConnector.repository;

import com.swapnil.derbyJDBCConnector.model.Circle;

import java.sql.*;

public class DAO {
  public Circle getCircle(int circleId) {
    Connection connection = null;
    try {
      //1. Instantiate driver
      String driver = "org.apache.derby.jdbc.ClientDriver";
      Class.forName(driver).newInstance();

      //2. Create connection by specifying connection string
      connection = DriverManager.getConnection("jdbc:derby://localhost:1527/db");

      //3. Prepared statement instead of firing query directly to avoid sql injection
      PreparedStatement statement = connection.prepareStatement("select * from Circle where id=?");
      statement.setInt(1, circleId);

      //4. Execute query and read result set
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        Circle circle = new Circle(circleId, resultSet.getString("name"));
        resultSet.close();
        statement.close();
        return circle;
      }
      return null;
    } catch (Exception ex) {
      throw new RuntimeException();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
      }
    }
  }
}
