package idatt2106.systemutvikling.sparesti.enums;

import lombok.Getter;

/**
 * Enum for the category of a transaction.
 */
@Getter
public enum TransactionCategory {
  GROCERIES("Groceries"),
  RESTAURANT_AND_CAFE("Restaurant and Cafe"),
  TRANSPORTATION("Transportation"),
  SHOPPING("Shopping"),
  ENTERTAINMENT("Entertainment"),
  INCOME("Income"),
  LOAN_DONATIONS("Loan and Donations"),
  OTHER("Other"),
  NOT_CATEGORIZED("Not categorized");

  private final String category;

  /**
   * Constructor for TransactionCategory.
   *
   * @param category the category of the transaction
   */
  TransactionCategory(String category) {
    this.category = category;
  }

}
