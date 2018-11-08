package com.swapnil.derbyspringconnector.demo.repository;


import com.swapnil.derbyspringconnector.demo.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DAO {
  @Autowired
  DataSource dataSource;

  public Circle getCircle(int circleId) {
    Connection connection = null;
    try {
      //1. Create connection by specifying connection string
      connection = dataSource.getConnection();

      //2. Prepared statement instead of firing query directly to avoid sql injection
      PreparedStatement statement = connection.prepareStatement("select * from Circle where id=?");
      statement.setInt(1, circleId);

      //3. Execute query and read result set
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
