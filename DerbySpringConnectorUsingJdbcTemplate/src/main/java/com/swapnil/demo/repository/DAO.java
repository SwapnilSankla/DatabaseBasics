package com.swapnil.demo.repository;


import com.swapnil.demo.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class DAO {
  private static final String query = "select * from Circle where id=?";

  @Autowired
  DataSource dataSource;

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @PostConstruct
  void setJdbcTemplateDataSource() {
    jdbcTemplate.setDataSource(dataSource);
  }

  public Optional<Circle> getCircleUsingResultSetExtractor(int circleId) {
    // Manually creating prepared statement
    return jdbcTemplate.query(getPreparedStatementCreator(circleId), null, new CircleResultSetExtractor(circleId));
  }

  public Optional<Circle> getCircleUsingRowMapper(int circleId) {
    try {
      // No need to create prepared statement, template internally uses the same
      return jdbcTemplate.queryForObject(query, new Object[]{circleId}, new CircleRowMapper());
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  public void insertCircle(Circle circle) {
    // Using named jdbc template as with that we can supply parameters explicitly instead of just ?
    //jdbcTemplate.update("INSERT INTO Circle values(?,?)", new Object[] { circle.getId(), circle.getName()});

    SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("id", circle.getId())
        .addValue("name", circle.getName());
    namedParameterJdbcTemplate.update("INSERT INTO Circle values(:id,:name)", mapSqlParameterSource);
  }

  public List<Circle> getAllCircles() {
    try {
      List<Optional<Circle>> result = jdbcTemplate.query("SELECT * from Circle", new CircleRowMapper());
      return result.stream().filter(x -> x.isPresent()).map(y -> y.get()).collect(toList());
    } catch (DataAccessException e) {
      return new ArrayList<>();
    }
  }

  private PreparedStatementCreator getPreparedStatementCreator(int circleId) {
    return connection -> {
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, circleId);
      return statement;
    };
  }
}

