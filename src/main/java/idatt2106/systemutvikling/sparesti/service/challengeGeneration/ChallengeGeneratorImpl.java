package idatt2106.systemutvikling.sparesti.service.challengeGeneration;

import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.ChallengeData;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class ChallengeGeneratorImpl implements ChallengeGenerator {

  private final int minDailyAmount = 10;
  private final int maxDailyAmount = 500;

  @Override
  public void generateDailyChallenges(ChallengeData challengeData) {
    //********** Data Collection. **********//
    int duration = 1;
    double difficulty = calcDifficulty(challengeData.getChallengeCompletionRate());
    double amount = calcAmount(
        minDailyAmount,
        maxDailyAmount,
        duration,
        challengeData.getMonthlySavingsGoal(),
        challengeData.getThisMonthTotalSavings(),
        challengeData.getThisMonthPlannedSavings(),
        ChronoUnit.DAYS.between(challengeData.getCurrentDateTime(),
            challengeData.getEndOfThisMonth()),
        ChronoUnit.DAYS.between(challengeData.getStartOfThisMonth(),
            challengeData.getEndOfThisMonth())
    );
    double adjustedAmount = amount * difficulty;

    ChallengeTheme theme = calculateChallengeTheme();

    //********** Generate Challenge. **********//
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = LocalDateTime.now().plusDays(1);

    //********** Data Storage. **********//

  }

  @Override
  public void generateWeeklyChallenges(ChallengeData challengeData) {
    // Generate weekly challenges
  }

  @Override
  public void generateMonthlyChallenges(ChallengeData challengeData) {
    // Generate monthly challenges
  }

  @Override
  public void generateRandomChallenges(ChallengeData challengeData) {
    // Generate random challenges
  }

  @Override
  public void generateInstantChallenge(ChallengeData challengeData) {
    // Generate instant challenge
  }


  /**
   * Calculates the challenge difficulty based on the challenge completion rate and the challenge
   * acceptance rate. 2/3 completed  challenges gives 1.0 difficulty. 1/3 completed challenges gives
   * 0.5 difficulty. 100% completed challenges gives 1.5 difficulty.
   *
   * @param challengeCompletionRate Percentage of challenges completed with goal sum reached.
   * @return The challenge difficulty. 1.0 is normal difficulty, 0.5 is easy, and 1.5 is hard.
   */
  public double calcDifficulty(double challengeCompletionRate) {
    double difficulty = 1.5; // 2/3 completed gives 1.0 difficulty
    difficulty = difficulty * challengeCompletionRate;

    if (difficulty < 0.5) {
      return 0.5;
    } else if (!(difficulty > 1.5)) {
      return difficulty;
    } else {
      return 1.5;
    }
  }

  /**
   * Calculates the optimal challenge amount based on the user's monthly goal, planned savings, and
   * the number of days left in the month.
   *
   * @param min                     The minimum amount the challenge can be.
   * @param max                     The maximum amount the challenge can be.
   * @param challengeDurationInDays The duration of the challenge in days.
   * @param monthlyGoal             The user's monthly goal.
   * @param monthlyPlannedSavings   The user's already planned savings for the month.
   * @param daysLeftInMonth         The number of days left in the month.
   * @return max >= optimalChallengeAmount >= min
   */
  public double calcAmount(int min, int max, int challengeDurationInDays, double monthlyGoal,
      double thisMonthTotalSavings, double monthlyPlannedSavings, long daysLeftInMonth,
      long daysInMonth) {

    double optimalAmount = (monthlyGoal / daysInMonth) * challengeDurationInDays;
    double amountToReachGoal =
        ((monthlyGoal - thisMonthTotalSavings - monthlyPlannedSavings) / daysLeftInMonth)
            * challengeDurationInDays;

    // Average the two amounts to get the optimal challenge amount
    double calculatedAmount = (optimalAmount + amountToReachGoal) / 2;

    if (calculatedAmount < min) {
      return min;
    } else if (calculatedAmount > max) {
      return max;
    } else {
      return calculatedAmount;
    }
  }

  public ChallengeTheme calculateChallengeTheme(
      double challengeAmount,
      int challengeDurationInDays,
      List<Transaction> transactions,
      Map<TransactionCategory, Double> categoryExpensesRatio,
      Map<TransactionCategory, Double> pastChallengesByCategoryPercentage,
      Map<TransactionCategory, Double> pastChallengesByCategoryAcceptedPercentage) {
    try {
      //********** OpenAI. **********//
      String prompt = "Choose a theme based on the following data:\n"
          + "Challenge amount: " + challengeAmount + "\n"
          + "Challenge duration in days: " + challengeDurationInDays + "\n"
          + "Transactions: " + transactions + "\n"
          + "Category expenses ratio: " + categoryExpensesRatio + "\n"
          + "Past challenges by category percentage: " + pastChallengesByCategoryPercentage + "\n"
          + "Past challenges by category accepted percentage: "
          + pastChallengesByCategoryAcceptedPercentage + "\n"
          + "Choose one of the following themes: " + ChallengeTheme.getAllThemes() + "\n"
          + "Choose a theme based on the data provided. "
          + "Only respond with the theme, nothing else.";


    } catch (Exception e) {

    }

    return null;
  }

}
