package idatt2106.systemutvikling.sparesti.model;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import jdk.jfr.Percentage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChallengeData {

  private String username;
  private List<ChallengeDAO> activeChallenges;
  private List<ChallengeDAO> inactiveChallenges;
  private int activeChallengesCount;
  private int inactiveChallengesCount;
  private double challengeCompletionRate;
  private double challengeAcceptanceRate;
  private double thisMonthTotalSavings;
  private double monthlySavingsGoal;
  private double thisMonthPlannedSavings;
  private double monthlyIncome;
  private double monthlyFixedExpenses;
  private LocalDateTime startOfThisMonth;
  private LocalDateTime endOfThisMonth;
  private LocalDateTime currentDateTime;
  private List<Transaction> transactions;
  private Map<TransactionCategory, Double> categoryExpensesRatio;
  private Map<TransactionCategory, Double> pastChallengesByCategoryPercentage;
  private Map<TransactionCategory, Double> pastChallengesByCategoryAcceptedPercentage;
  private Map<ChallengeTheme, Double> pastChallengesByThemeRatio;

}
