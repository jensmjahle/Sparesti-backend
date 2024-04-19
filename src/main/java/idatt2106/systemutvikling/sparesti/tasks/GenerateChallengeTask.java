package idatt2106.systemutvikling.sparesti.tasks;

import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GenerateChallengeTask {
private final Logger logger = Logger.getLogger(GenerateChallengeTask.class.getName());

@Scheduled(cron = "${generate.daily.challenges.cron.expression:0 0 0 1 * *}")
public void dailyChallenges() {
  logger.info("Generating challenges...");
}

@Scheduled(cron = "${generate.weekly.challenges.cron.expression:0 0 0 * * 1}")
public void weeklyChallenges() {
  logger.info("Generating challenges...");
}

@Scheduled(cron = "${generate.monthly.challenges.cron.expression:0 0 0 1 * *}")
public void monthlyChallenges() {
  logger.info("Generating challenges...");
}

  @Scheduled(fixedRateString = "${generate.random.challenges.rate.in.milliseconds:60000}")
  public void randomChallenges() {
    logger.info("Generating challenges...");
  }

}
