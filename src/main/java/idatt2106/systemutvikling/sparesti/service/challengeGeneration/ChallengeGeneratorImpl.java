package idatt2106.systemutvikling.sparesti.service.challengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.model.challengeGeneration.ChallengeData;
import idatt2106.systemutvikling.sparesti.model.challengeGeneration.ChallengeTitleAndDescription;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.service.OpenAIService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Challenge generator.
 */
@Component
public class ChallengeGeneratorImpl {

  private final OpenAIService openAIService;
  private final ChallengeRepository challengeRepository;
  Logger logger = Logger.getLogger(ChallengeGeneratorImpl.class.getName());

  /**
   * Instantiates a new Challenge generator.
   *
   * @param openAIService       the open ai service
   * @param challengeRepository the challenge repository
   */
  @Autowired
  public ChallengeGeneratorImpl(OpenAIService openAIService,
      ChallengeRepository challengeRepository) {
    this.openAIService = openAIService;
    this.challengeRepository = challengeRepository;
  }

  /**
   * Generate challenge.
   *
   * @param data     the data
   * @param duration the duration
   */
  public void generateChallenge(ChallengeData data, int duration) {
    //**********Set Global Restrictions. **********//
    int minDailyAmount = 10;
    int maxDailyAmount = 500;
    int maxInactiveChallenges = 2;

    //********** Check if user has too many active challenges. **********//
    if (data.getInactChalCount() > maxInactiveChallenges) {
      return;
    }

    //********** Data Collection. **********//
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = LocalDateTime.now().plusDays(duration);
    double temperature = calcTemp(duration);

    double difficulty = calcDifficulty(data.getChalComplRate());
    double amount = calcAmount(minDailyAmount * duration, maxDailyAmount * duration,
        duration,
        data.getMthSavGoal(),
        data.getThisMthTotSav(),
        data.getThisMthPLNDSav(),
        ChronoUnit.DAYS.between(data.getToday(),
            data.getEndOfMth()),
        ChronoUnit.DAYS.between(data.getStartOfMth(),
            data.getEndOfMth())
    );
    TransactionCategory themeCategory = calcThemeCategory(data.getCatExpRatio(),
        data.getPastChalByCatRatio(),
        data.getPastChalByCatAccRatio());
    List<ChallengeTheme> allThemesInCategory = ChallengeTheme.getAllThemesFromCategory(
        themeCategory);

    //********** Data Processing.. **********//
    double adjustedAmount = amount * difficulty * temperature;
    ChallengeTheme theme = calcTheme(themeCategory, data.getTrxs(), adjustedAmount,
        allThemesInCategory, duration);
    ChallengeTitleAndDescription titleAndDescription = generateTitleAndDescription(theme, amount,
        data.getTrxs(), start, end);

    //********** Generate Challenge. **********//
    ChallengeDAO challenge = new ChallengeDAO();
    challenge.setChallengeTitle(titleAndDescription.getTitle());
    challenge.setChallengeDescription(titleAndDescription.getDescription());
    challenge.setGoalSum((long) amount);
    challenge.setCurrentSum(0L);
    challenge.setStartDate(start);
    challenge.setExpirationDate(end);
    challenge.setTheme(theme);
    challenge.setActive(false);
    challenge.setRecurringInterval(RecurringInterval.NONE);
    challenge.setUserDAO(data.getUser());

    //********** Data Storage. **********//
    challengeRepository.save(challenge);
  }

  /**
   * Calculates the challenge difficulty based on the challenge completion rate and the challenge
   * acceptance rate. 2/3 completed  challenges gives 1.0 difficulty. 1/3 completed challenges gives
   * 0.5 difficulty. 100% completed challenges gives 1.5 difficulty.
   *
   * @param challengeCompletionRate Percentage of challenges completed with goal sum reached.
   * @return The challenge difficulty. 1.0 is normal difficulty, 0.5 is easy, and 1.5 is hard.
   */
  private double calcDifficulty(double challengeCompletionRate) {
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
   * Calculates the challenge temperature based on the duration of the challenge. The temperature
   * decreases over time. 1 day ≈ 0.9, 7 days ≈ 0.5, 30 days ≈ 0.1
   *
   * @param duration The duration of the challenge in days.
   * @return The challenge temperature. 1.0 is normal temperature, 0.5 is medium, and 0.1 is low.
   */
  private double calcTemp(int duration) {
    return Math.pow(Math.E, -0.1 * duration);
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
  private double calcAmount(int min, int max, int challengeDurationInDays, double monthlyGoal,
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

  /**
   * Calculates the challenge theme-category based on the user's expenses ratio, past challenges by
   * category ratio, and past challenges by category accepted ratio. The theme-category with the
   * highest weighted value is chosen.
   *
   * @param expRatio         Map of the user's expenses ratio for each category
   * @param pastChalRatio    Map of the user's past challenges by category ratio
   * @param pastChalAccRatio Map of the user's past challenges by category accepted ratio
   * @return The theme-category with the highest weighted value
   */
  private TransactionCategory calcThemeCategory(
      Map<TransactionCategory, Double> expRatio,
      Map<TransactionCategory, Double> pastChalRatio,
      Map<TransactionCategory, Double> pastChalAccRatio) {
    try {
      Map<TransactionCategory, Double> weightedValues = new HashMap<>();

      // Calculate weighted values for each category
      for (TransactionCategory category : TransactionCategory.values()) {
        // Skip the NOT_CATEGORIZED category
        if (category.equals(TransactionCategory.NOT_CATEGORIZED)) {
          continue;
        }
        double expensesRatio = expRatio.getOrDefault(category, 0.0);
        double pastChallengesRatio = pastChalRatio.getOrDefault(category, 0.0);
        double pastChallengesAcceptedRatio = pastChalAccRatio
            .getOrDefault(category, 0.0);

        double weightedValue =
            1 + expensesRatio - pastChallengesRatio + pastChallengesAcceptedRatio;
        weightedValues.put(category, weightedValue);
      }

      // Choose the category with the highest weighted value
      return Collections.max(weightedValues.entrySet(),
          Map.Entry.comparingByValue()).getKey();

    } catch (Exception e) {
      logger.severe("Failed to calculate theme category: " + e.getMessage());
      return TransactionCategory.OTHER;
    }
  }

  /**
   * Uses the OpenAI service to calculate the challenge theme based on the user's transaction,
   * adjusted amount, duration, and available themes. If no theme is suitable, the base theme for
   * the category is returned.
   *
   * @param category            TransactionCategory
   * @param transactions        List of past transactions for the user
   * @param adjustedAmount      The goal sum in NOK for the challenge
   * @param allThemesInCategory List of available challenge themes in the category
   * @param duration            The duration in days for the challenge
   * @return The selected challenge theme
   */
  private ChallengeTheme calcTheme(TransactionCategory category, List<Transaction> transactions,
      double adjustedAmount, List<ChallengeTheme> allThemesInCategory, int duration) {
    try {
      String prompt =
          "Based on the user's transaction history, adjusted amount, duration in days, and available themes,"
              + " select a suitable challenge theme that can help the user improve their spending habits. "
              + "The user has provided the following information:\n"
              + "\n"
              + "Chosen Category: " + category + "\n"
              + "Transactions (A list of transactions made by the user.) : " + transactions + "\n"
              + "Adjusted Amount (The goal sum in NOK. This should be the goal sum of this challenge.) :"
              + adjustedAmount + "\n"
              + "All Themes (A list of available challenge themes that belong to the chosen category) : "
              + allThemesInCategory + "\n"
              + "Duration: The duration in days for the challenge." + duration + "\n"
              + "Given this information, choose a challenge theme that aligns with the user's spending habits and financial goals."
              + "Consider factors such as the user's transaction history, the adjusted amount, the challenge Category, and the duration of the challenge. "
              + "Ensure that the selected theme provides a reasonable level of difficulty based on these factors.\n"
              + "Only reply with the theme name from the list of available themes. If no theme is suitable, reply with 'NO_THEME'\n";

      String response = openAIService.chat(prompt);

      if (response.equals("NO_THEME")) {
        return ChallengeTheme.getBaseTheme(category);
      }
      return ChallengeTheme.valueOf(response);
    } catch (Exception e) {
      logger.warning("Failed to chose challenge theme: " + e.getMessage());
      return ChallengeTheme.getBaseTheme(category);
    }

  }

  /**
   * Uses the OpenAI service to generate a title and description for the challenge based on
   * parameters
   *
   * @param theme        The selected challenge theme
   * @param amount       The goal sum in NOK for the challenge
   * @param transactions List of past transactions for the user
   * @param start        The start date for the challenge
   * @param end          The end date for the challenge
   * @return A ChallengeTitleAndDescription object with the generated title and description
   */
  private ChallengeTitleAndDescription generateTitleAndDescription(ChallengeTheme theme,
      double amount,
      List<Transaction> transactions, LocalDateTime start, LocalDateTime end) {
    try {
      String prompt =
          "Based on the user's transaction history, adjusted amount, duration in days, and selected theme,"
              + " generate a suitable title and description for the challenge. It is important to follow the theme. The user has provided the following information:\n"
              + "\n"
              + "Chosen Theme: " + theme + "\n"
              + "Transactions (A list of transactions made by the user.) : " + transactions + "\n"
              + "Adjusted Amount (The goal sum in NOK. This should be the goal sum of this challenge.) :"
              + amount + "\n"
              + "Start Date: The start date for the challenge." + start + "\n"
              + "End Date: The end date for the challenge." + end + "\n"
              + "Given this information, generate a title and description for the challenge that aligns with the given theme."
              + "Consider factors such as the user's transaction history, the adjusted amount, the challenge theme, the weekdays of the challenge, and the duration of the challenge. "
              + "Ensure that the title and description provide a reasonable level of difficulty based on these factors.\n"
              + "The title should be fun an encouraging and the description should be informative and motivating.\n"
              + "Title length should be between 10 and 45 characters and description length should be between 50 and 200 characters.\n"
              + "Avoid using the theme in capital letters in the title and description. If you use it translate to norwegian and write it normally.\n"
              + "Only reply with the title and description on this format: Title;Description  \n"
              + "If you use any numbers round them to the nearest whole number. \n"
              + "Always include at least 10 emojis \n"
              + "If you are unable to generate a title and description, reply with 'NO_TITLE_DESCRIPTION'\n"
              + "Give your answer in norwegian\n"
              + "\n";

      String response = openAIService.chat(prompt);

      if (response.equals("NO_TITLE_DESCRIPTION")) {
        String title = theme.getStandardMessage();
        String description =
            "Prøv å spare " + Math.round(amount) + " NOK i løpet av " + ChronoUnit.DAYS.between(
                start, end)
                + " dager ved å redusere dine utgifter i kategorien " + theme.getExpenseCategory()
                .getCategory();
        return new ChallengeTitleAndDescription(title, description);
      }

      String[] titleAndDescription = response.split(";");
      return new ChallengeTitleAndDescription(titleAndDescription[0], titleAndDescription[1]);
    } catch (Exception e) {
      logger.warning("Failed to generate title and description: " + e.getMessage());
      String title = theme.getStandardMessage();
      String description =
          "Try to save " + Math.round(amount) + " NOK in " + ChronoUnit.DAYS.between(start, end)
              + " days by reducing your " + theme.getExpenseCategory().getCategory()
              + " expenses.";
      return new ChallengeTitleAndDescription(title, description);
    }
  }
}
