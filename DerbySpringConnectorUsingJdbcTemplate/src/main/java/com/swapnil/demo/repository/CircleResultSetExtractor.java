package com.swapnil.demo.repository;

import com.swapnil.demo.model.Circle;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CircleResultSetExtractor implements ResultSetExtractor<Optional<Circle>> {
  private int circleId;

  public CircleResultSetExtractor(int circleId) {
    this.circleId = circleId;
  }

  @Nullable
  @Override
  public Optional<Circle> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
    if (resultSet.next()) {
      return Optional.of(new Circle(circleId, resultSet.getString("name")));
    }
    return Optional.empty();
  }
}
