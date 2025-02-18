package idatt2106.systemutvikling.sparesti.service.challengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.model.challengeGeneration.ChallengeData;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.service.ChallengeLogService;
import idatt2106.systemutvikling.sparesti.service.ChallengeService;
import idatt2106.systemutvikling.sparesti.service.ManualSavingService;
import idatt2106.systemutvikling.sparesti.service.TransactionService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for generating challenges for users.
 */
@Service
@AllArgsConstructor
public class GenerateChallengeService {

  private final UserRepository userRepository;
  private final Logger logger = Logger.getLogger(GenerateChallengeService.class.getName());
  private final ChallengeGeneratorImpl challengeGenerator;
  private final ChallengeService challengeService;
  private final ChallengeLogService challengeLogService;
  private final TransactionService transactionService;
  private final ManualSavingService manualSavingService;

  /**
   * Generates one daily challenge for all users in the database. This method is called once a day
   * by GenerateChallengeTask.
   */
  public void generateDailyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), 1);
        } catch (Exception e) {
          logger.severe("Failed to generate daily challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate daily challenges" + e.getMessage());
    }
  }

  /**
   * Generates one weekly challenge for all users in the database. This method is called once a week
   * by GenerateChallengeTask.
   */
  public void generateWeeklyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), 7);
        } catch (Exception e) {
          logger.severe("Failed to generate weekly challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate weekly challenges" + e.getMessage());
    }
  }

  /**
   * Generates one monthly challenge for all users in the database. This method is called once a
   * month by GenerateChallengeTask.
   */
  public void generateMonthlyChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      int daysInMonth = LocalDateTime.now().toLocalDate().lengthOfMonth();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), daysInMonth);
        } catch (Exception e) {
          logger.severe("Failed to generate monthly challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate monthly challenges" + e.getMessage());
    }
  }

  /**
   * Generates random challenges for all users in the database.
   */
  public void generateRandomChallenges() {
    try {
      List<UserDAO> users = userRepository.findAll();
      for (UserDAO user : users) {
        try {
          challengeGenerator.generateChallenge(initializeChallengeData(user), 1);
        } catch (Exception e) {
          logger.severe("Failed to generate random challenges for user " + user.getUsername() + "."
              + e.getMessage());
        }
      }
    } catch (Exception e) {
      logger.severe("Failed to generate random challenges" + e.getMessage());
    }
  }

  /**
   * Generates a challenge for a user.
   *
   * @param user The user to generate the challenge for.
   */
  public void generateInstantChallenge(UserDAO user) {
    try {
      challengeGenerator.generateChallenge(initializeChallengeData(user), 1);
    } catch (Exception e) {
      logger.severe("Failed to generate instant challenge for user " + user.getUsername() + "."
          + e.getMessage());
    }
  }

  /**
   * Initializes the ChallengeData object for a user. The ChallengeData object is used to generate
   * challenges for the user.
   *
   * @param user The user to initialize the ChallengeData object for.
   * @return The initialized ChallengeData object.
   */
  public ChallengeData initializeChallengeData(UserDAO user) {
    List<ChallengeDAO> activeChallenges = challengeService.getChallengesByActiveAndUsername(
        user.getUsername(), true);
    List<ChallengeDAO> inactiveChallenges = challengeService.getChallengesByActiveAndUsername(
        user.getUsername(), false);
    int activeChallengesCount = activeChallenges.size();
    int inactiveChallengesCount = inactiveChallenges.size();
    double challengeCompletionRate = challengeLogService.getChallengeCompletionRate(
        user.getUsername());
    double challengeAcceptanceRate = challengeLogService.getChallengeAcceptanceRate(
        user.getUsername());
    double thisMonthTotalSavings = getThisMonthTotalSavings(user.getUsername());
    double monthlySavingsGoal = user.getMonthlySavings();
    double thisMonthPlannedSavings = getThisMonthPlannedSavings(user.getUsername());
    double monthlyIncome = user.getMonthlyIncome();
    double monthlyFixedExpenses = user.getMonthlyFixedExpenses();
    LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0)
        .withSecond(0);
    LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(1).plusMonths(1).withHour(0)
        .withMinute(0).withSecond(0);
    LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
    List<Transaction> transactions = transactionService.getTransactionsCategorized(
        TransactionService.DEFAULT_EXPENSES_TIME_SPAN, user.getUsername());
    Map<TransactionCategory, Double> categoryExpenseRatio = getCategoryExpenseRatio(transactions);
    Map<TransactionCategory, Double> pastChallengesByCategoryRatio = challengeLogService.getChallengesByCategoryRatio(
        user.getUsername());
    Map<TransactionCategory, Double> pastChallengesByCategoryAcceptedRatio = challengeLogService.getChallengesByCategoryAcceptedRatio(
        user.getUsername());
    Map<ChallengeTheme, Double> pastChallengesByThemeRatio = challengeLogService.getChallengesByThemeRatio(
        user.getUsername());

    return new ChallengeData(user, activeChallenges, inactiveChallenges, activeChallengesCount,
        inactiveChallengesCount, challengeCompletionRate,
        challengeAcceptanceRate, thisMonthTotalSavings, monthlySavingsGoal, thisMonthPlannedSavings,
        monthlyIncome, monthlyFixedExpenses, startOfMonth, endOfMonth, today, transactions,
        categoryExpenseRatio,
        pastChallengesByCategoryRatio, pastChallengesByCategoryAcceptedRatio,
        pastChallengesByThemeRatio);
  }

  /**
   * Gets the total savings for a user this month. The total savings is the sum of the current sum
   * of all active challenges and the achieved sum of all completed challenges this month.
   *
   * @param username The username of the user to get the total savings for.
   * @return The total savings for the user this month.
   */
  private double getThisMonthTotalSavings(String username) {
    List<ChallengeDAO> challenges = challengeService.getChallengesStartedAfterDate(
        LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0),
        username);
    List<ChallengeLogDAO> challengeLogs = challengeLogService.getChallengesByCompletionDateAfterAndUsername(
        username, LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0));
    double totalSavings = 0;
    for (ChallengeDAO challenge : challenges) {
      totalSavings += challenge.getCurrentSum();
    }
    for (ChallengeLogDAO challengeLog : challengeLogs) {
      totalSavings += challengeLog.getChallengeAchievedSum();
    }
    double manualSavings = manualSavingService.getThisMonthTotalManualSavings(username);
    totalSavings += manualSavings;

    return totalSavings;
  }

  private double getThisMonthPlannedSavings(String username) {
    List<ChallengeDAO> challenges = challengeService.getChallengesStartedAfterDate(
        LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0),
        username);

    double totalPlannedSavings = 0;

    for (ChallengeDAO challenge : challenges) {
      totalPlannedSavings += challenge.getGoalSum() - challenge.getCurrentSum();
    }
    return totalPlannedSavings;
  }

  /**
   * Gets the ratio of expenses for each category in a list of transactions. The ratio is calculated
   * by dividing the amount of expenses in a category by the total amount of expenses.
   *
   * @param transactions The list of transactions to get the category expense ratio for.
   * @return A map with the category as the key and the ratio of expenses in that category as the
   * value.
   */
  private Map<TransactionCategory, Double> getCategoryExpenseRatio(List<Transaction> transactions) {
    Map<TransactionCategory, Double> categoryExpense = new HashMap<>();
    double totalExpenses = 0;
    for (Transaction transaction : transactions) {
      categoryExpense.merge(transaction.getCategory(), Double.valueOf(transaction.getAmount()),
          Double::sum);
      totalExpenses += transaction.getAmount();
    }
    if (totalExpenses == 0) {
      return null;
    }
    for (Map.Entry<TransactionCategory, Double> entry : categoryExpense.entrySet()) {
      entry.setValue(entry.getValue() / totalExpenses);
    }

    return categoryExpense;
  }

}
