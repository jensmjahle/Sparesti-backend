package idatt2106.systemutvikling.sparesti.tasks;

import idatt2106.systemutvikling.sparesti.service.challengeGeneration.GenerateChallengeService;
import idatt2106.systemutvikling.sparesti.service.SmsService;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GenerateChallengeTask {
private final Logger logger = Logger.getLogger(GenerateChallengeTask.class.getName());
private final GenerateChallengeService generateChallengeService;
private final SmsService smsService;

  /**
   * Task to generate daily challenges. Runs every day at 06.00.
   */
  @Scheduled(cron = "${generate.daily.challenges.cron.expression:0 0 0 1 * *}")
public void dailyChallenges() {
  logger.info("Generating daily challenges...");
  generateChallengeService.generateDailyChallenges();
}

  /**
   * Task to generate weekly challenges. Runs every Monday at 06.00.
   */
  @Scheduled(cron = "${generate.weekly.challenges.cron.expression:0 0 0 * * 1}")
public void weeklyChallenges() {
  logger.info("Generating weekly challenges...");
  generateChallengeService.generateWeeklyChallenges();
}

  /**
   * Task to generate monthly challenges. Runs every first day of the month at 06.00.
   */
  @Scheduled(cron = "${generate.monthly.challenges.cron.expression:0 0 0 1 * *}")
public void monthlyChallenges() {
  logger.info("Generating monthly challenges...");
  generateChallengeService.generateMonthlyChallenges();
}

  /**
   * Task to generate random challenges. Runs every hour by default.
   */
  @Scheduled(fixedRateString = "${generate.random.challenges.rate.in.milliseconds:60000}")
  public void randomChallenges() {
    logger.info("Generating random challenges...");
    generateChallengeService.generateRandomChallenges();
  }

}
