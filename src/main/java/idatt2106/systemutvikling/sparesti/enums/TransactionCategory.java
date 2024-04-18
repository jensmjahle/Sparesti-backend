package idatt2106.systemutvikling.sparesti.enums;

import lombok.Getter;

@Getter
public enum TransactionCategory {
  GROCERIES("Groceries"),
  RESTAURANT_AND_CAFE("Restaurant and Cafe"),
  TRANSPORTATION("Transportation"),
  SHOPPING("Shopping"),
  ENTERTAINMENT("Entertainment"),
  INCOME("Income"),
  LOAN_DONATIONS("Loan and Donations"),
  OTHER("Other");

  private final String category;

  TransactionCategory(String category) {
    this.category = category;
  }

}
