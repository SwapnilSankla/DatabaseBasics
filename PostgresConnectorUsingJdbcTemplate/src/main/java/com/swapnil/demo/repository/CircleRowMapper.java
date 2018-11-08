package com.swapnil.demo.repository;

import com.swapnil.demo.model.Circle;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class CircleRowMapper implements RowMapper<Optional<Circle>> {

  @Nullable
  @Override
  public Optional<Circle> mapRow(ResultSet resultSet, int i) throws SQLException {
    // No need to call resultSet.next() as the result set is passed per row
    try {
      return Optional.of(new Circle(resultSet.getInt("id"), resultSet.getString("name")));
    } catch (SQLException e) {
      return Optional.empty();
    }
  }
}
