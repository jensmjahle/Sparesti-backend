package idatt2106.systemutvikling.sparesti.model.challengeGeneration;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.enums.ChallengeTheme;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Model class for the ChallengeData entity.
 */
@Getter
@Setter
@AllArgsConstructor
public class ChallengeData {

  private UserDAO user; // User
  private List<ChallengeDAO> actChal; // Active Challenges
  private List<ChallengeDAO> inactChal; // Inactive Challenges
  private int actChalCount; // Active Challenges Count
  private int inactChalCount; // Inactive Challenges Count
  private double chalComplRate; // Challenge Completion Rate
  private double chalAccRate; // Challenge Acceptance Rate
  private double thisMthTotSav; // This Month Total Savings
  private double mthSavGoal; // Monthly Savings Goal
  private double thisMthPLNDSav; // This Month Planned Savings
  private double mthInc; // Monthly Income
  private double mthFixedExp; // Monthly Fixed Expenses
  private LocalDateTime startOfMth; // Start Of This Month
  private LocalDateTime endOfMth; // End Of This Month
  private LocalDateTime today; // Today
  private List<Transaction> trxs; // Transactions
  private Map<TransactionCategory, Double> catExpRatio; // Category Expense Ratio
  private Map<TransactionCategory, Double> pastChalByCatRatio; // Past Challenges By Category Ratio
  private Map<TransactionCategory, Double> pastChalByCatAccRatio; // Past Challenges By Category Accepted Ratio
  private Map<ChallengeTheme, Double> pastChalByThemeRatio; // Past Challenges By Theme Ratio

}
