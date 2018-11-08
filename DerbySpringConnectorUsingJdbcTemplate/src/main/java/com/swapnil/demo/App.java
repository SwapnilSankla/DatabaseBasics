package com.swapnil.demo;

import com.swapnil.demo.model.Circle;
import com.swapnil.demo.repository.DAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

  @Autowired
  DAO dao;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

  @Override
  public void run(String... args) throws Exception {
    int circleId = 1;
    log.info("Get circle {} using result set extractor", circleId);
    log(circleId, dao.getCircleUsingResultSetExtractor(circleId));

    circleId = 1;
    log.info("Get circle {} using row mapper", circleId);
    log(circleId, dao.getCircleUsingRowMapper(circleId));

    circleId = dao.getAllCircles().size() + 1;
    log.info("Create new circle with id {}", circleId);
    dao.insertCircle(new Circle(circleId, "Circle" + circleId));

    log.info("List all circles");
    dao.getAllCircles().forEach(c -> log(c.getId(), Optional.of(c)));
  }

  private void log(int circleId, Optional<Circle> circle) {
    circle.ifPresent(c -> log.info(c.getName()));
    if(!circle.isPresent()) {
      log.error("Circle with id {} absent", circleId);
    }
  }
}
