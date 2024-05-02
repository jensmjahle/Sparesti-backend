package idatt2106.systemutvikling.sparesti.tasks;

import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.MilestoneService;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
public class MoveToLogTask {

  private final Logger logger = Logger.getLogger(MoveToLogTask.class.getName());
  MilestoneService milestoneService;
  ChallengeService challengeService;

  @Scheduled(cron = "${database.refactor.cron.expression:0 0 0 * * *}")
  public void moveExpiredMilestonesToLog() {
    try {
      milestoneService.moveExpiredMilestonesToLog();
    } catch (Exception e) {
      logger.severe("An error occurred while moving expired milestones to log: " + e.getMessage());
    }
  }

  @Scheduled(cron = "${database.refactor.cron.expression:0 0 0 * * *}")
  public void moveExpiredChallengesToLog() {
    try {
      challengeService.moveExpiredChallengesToLog();
    } catch (Exception e) {
      logger.severe("An error occurred while moving expired challenges to log: " + e.getMessage());
    }
  }

}
