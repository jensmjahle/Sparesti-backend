package idatt2106.systemutvikling.sparesti.tasks;

import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GenerateChallengeTask {
private Logger logger = Logger.getLogger(GenerateChallengeTask.class.getName());

@Scheduled(fixedRateString = "${generate.challenges.rate.in.milliseconds:6000}")
public void generateChallenges() {
  logger.info("Generating challenges...");
  // Generate challenges
}
}
