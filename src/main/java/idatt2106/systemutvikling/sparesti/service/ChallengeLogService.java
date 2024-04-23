package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import java.util.List;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChallengeLogService {
  private final ChallengeLogRepository challengeLogRepository;
 private final Logger logger = Logger.getLogger(ChallengeLogService.class.getName());


  public double getChallengeCompletionRate(String username) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository.findChallengeLogDAOByUserDAO_Username(username);
      if (challengeLogs.isEmpty()) {
        return 0;
      }
      double totalGoalSum = 0;
      double totalAchievedSum = 0;
      for (ChallengeLogDAO challengeLog : challengeLogs) {
        totalGoalSum += challengeLog.getGoalSum();
        totalAchievedSum += challengeLog.getChallengeAchievedSum()

      }
      return totalAchievedSum / totalGoalSum;
    } catch (Exception e) {
      logger.severe("Failed to get challenge completion rate for user " + username + ". " + e.getMessage());
      throw new RuntimeException("Failed to get challenge completion rate for user " + username + ". " + e.getMessage());
    }

  }
}
