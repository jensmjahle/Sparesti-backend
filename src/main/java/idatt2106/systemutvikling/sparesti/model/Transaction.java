package idatt2106.systemutvikling.sparesti.model;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import jakarta.validation.constraints.Positive;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Transaction {
  private Long transactionId;
  private Long accountNr;
  private String transactionTitle;
  private Date time;
  private Long debtorAccount;
  private String debtorName;
  private Long creditorAccount;
  private String creditorName;
  @Positive
  private Long amount;
  private String currency;
  private TransactionCategory category;

  public Transaction(Long transactionId, Long accountNr, String transactionTitle, Date time, Long debtorAccount, String debtorName, Long creditorAccount, String creditorName, Long amount, String currency) {
    this.transactionId = transactionId;
    this.accountNr = accountNr;
    this.transactionTitle = transactionTitle;
    this.time = time;
    this.debtorAccount = debtorAccount;
    this.debtorName = debtorName;
    this.creditorAccount = creditorAccount;
    this.creditorName = creditorName;
    this.amount = amount;
    this.currency = currency;
    this.category = TransactionCategory.NOT_CATEGORIZED;
  }
}
