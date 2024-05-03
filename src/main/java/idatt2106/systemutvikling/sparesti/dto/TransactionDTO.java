package idatt2106.systemutvikling.sparesti.dto;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for Transaction
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

  private Long transactionId;

  private String transactionTitle;

  private Date time;

  @NotNull
  private Long debtorAccount;

  private String debtorName;
  @NotNull
  private Long creditorAccount;

  private String creditorName;

  private Long amount;

  private String currency;

  private TransactionCategory transactionCategory;
}
