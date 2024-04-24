package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChallengeLogService {

  private final ChallengeLogRepository challengeLogRepository;
  private final Logger logger = Logger.getLogger(ChallengeLogService.class.getName());

  /**
   * Method to get the challenge completion rate for a user based on the challenge logs.
   *
   * @param username the username of the user
   * @return A double between 0 and 1 representing the challenge completion rate
   */
  public double getChallengeCompletionRate(String username) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository.findChallengeLogDAOByUserDAO_Username(
          username);
      if (challengeLogs.isEmpty()) {
        logger.warning("No challenge logs found for user " + username);
        return 0;
      }
      double totalGoalSum = 0;
      double totalAchievedSum = 0;
      for (ChallengeLogDAO challengeLog : challengeLogs) {
        totalGoalSum += challengeLog.getGoalSum();
        totalAchievedSum += challengeLog.getChallengeAchievedSum();
      }
      return totalAchievedSum / totalGoalSum;
    } catch (Exception e) {
      logger.severe(
          "Failed to get challenge completion rate for user " + username + ". " + e.getMessage());
      throw new RuntimeException(
          "Failed to get challenge completion rate for user " + username + ". " + e.getMessage());
    }

  }

  /**
   * Method to get the challenge acceptance rate for a user based on the challenge logs.
   *
   * @param username the username of the user
   * @return A double between 0 and 1 representing the challenge acceptance rate
   */
  public double getChallengeAcceptanceRate(String username) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository.findChallengeLogDAOByUserDAO_Username(
          username);
      if (challengeLogs.isEmpty()) {
        logger.warning("No challenge logs found for user " + username);
        return 0;
      }
      double totalAccepted = 0;
      double totalChallenges = challengeLogs.size();
      for (ChallengeLogDAO challengeLog : challengeLogs) {
        if (challengeLog.isAccepted()) {
          totalAccepted++;
        }
      }
      return totalAccepted / totalChallenges;
    } catch (Exception e) {
      logger.severe(
          "Failed to get challenge acceptance rate for user " + username + ". " + e.getMessage());
      throw new RuntimeException(
          "Failed to get challenge acceptance rate for user " + username + ". " + e.getMessage());
    }
  }

  public List<ChallengeLogDAO> getChallengesByCompletionDateAfterAndUsername(String username,
      LocalDateTime startDate) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository
          .findChallengeLogDAOSByCompletionDateAfterAndUserDAO_Username(startDate, username);
      if (challengeLogs.isEmpty()) {
        logger.warning("No challenge logs found for user " + username + " after " + startDate);
      }
      return challengeLogs;
    } catch (Exception e) {
      logger.severe("Failed to get challenges by completion date after " + startDate + " for user "
          + username + ". " + e.getMessage());
      throw new RuntimeException("Failed to get challenges by completion date after " + startDate
          + " for user " + username + ". " + e.getMessage());
    }
  }

  public Map<TransactionCategory, Double> getChallengesByCategoryRatio(String username) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository
          .findChallengeLogDAOByUserDAO_Username(username);

      Map<TransactionCategory, Double> logByCategoryRatio = new HashMap<>();

      for (ChallengeLogDAO challengeLog : challengeLogs) {
        TransactionCategory category = challengeLog.getTheme().getExpenseCategory();

        logByCategoryRatio.merge(category, 1.0, Double::sum);

      }
      for (Map.Entry<TransactionCategory, Double> entry : logByCategoryRatio.entrySet()) {
        entry.setValue(entry.getValue() / challengeLogs.size());
      }
      return logByCategoryRatio;
    } catch (Exception e) {
      logger.severe("Failed to get challenges by category ratio for user " + username + ". "
          + e.getMessage());
      throw new RuntimeException(
          "Failed to get challenges by category ratio for user " + username + ". "
              + e.getMessage());
    }
  }

  public Map<TransactionCategory, Double> getChallengesByCategoryAcceptedRatio(String username) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository
          .findChallengeLogDAOByUserDAO_Username(username);

      Map<TransactionCategory, Double> logByCategoryAcceptedRatio = new HashMap<>();
      Map<TransactionCategory, Double> logByCategorySum = new HashMap<>();

      for (ChallengeLogDAO challengeLog : challengeLogs) {
        if (challengeLog.isAccepted()) {
          TransactionCategory category = challengeLog.getTheme().getExpenseCategory();

          logByCategoryAcceptedRatio.merge(category, 1.0, Double::sum);
        }
        logByCategorySum.merge(challengeLog.getTheme().getExpenseCategory(),
            1.0, Double::sum);
      }
      for (Map.Entry<TransactionCategory, Double> entry : logByCategoryAcceptedRatio.entrySet()) {
        entry.setValue(entry.getValue() / logByCategorySum.get(entry.getKey()));
      }
      return logByCategoryAcceptedRatio;
    } catch (Exception e) {
      logger.severe(
          "Failed to get challenges by category accepted ratio for user " + username + ". "
              + e.getMessage());
      throw new RuntimeException(
          "Failed to get challenges by category accepted ratio for user " + username + ". "
              + e.getMessage());
    }
  }

  public Map<ChallengeTheme, Double> getChallengesByThemeRatio(String username) {
    try {
      List<ChallengeLogDAO> challengeLogs = challengeLogRepository
          .findChallengeLogDAOByUserDAO_Username(username);

      Map<ChallengeTheme, Double> logByThemeRatio = new HashMap<>();

      for (ChallengeLogDAO challengeLog : challengeLogs) {
        ChallengeTheme theme = challengeLog.getTheme();

        logByThemeRatio.merge(theme, 1.0, Double::sum);

      }
      for (Map.Entry<ChallengeTheme, Double> entry : logByThemeRatio.entrySet()) {
        entry.setValue(entry.getValue() / challengeLogs.size());
      }
      return logByThemeRatio;
    } catch (Exception e) {
      logger.severe("Failed to get challenges by theme ratio for user " + username + ". "
          + e.getMessage());
      throw new RuntimeException(
          "Failed to get challenges by theme ratio for user " + username + ". "
              + e.getMessage());
    }
  }
}