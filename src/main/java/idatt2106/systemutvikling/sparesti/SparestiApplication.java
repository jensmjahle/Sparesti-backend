package idatt2106.systemutvikling.sparesti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class SparestiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SparestiApplication.class, args);
  }

}
