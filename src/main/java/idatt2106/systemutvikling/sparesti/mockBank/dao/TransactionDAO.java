package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data access object for the Transaction entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mock_transaction")
public class TransactionDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "account_nr")
  private AccountDAO accountDAO;

  @Column(name = "transaction_title")
  private String transactionTitle;

  @Column(name = "time_stamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date time;

  @NotNull
  @Column(name = "debtor_account")
  private Long debtorAccount;

  @Column(name = "debtor_name")
  private String debtorName;

  @NotNull
  @Column(name = "creditor_account")
  private Long creditorAccount;

  @Column(name = "creditor_name")
  private String creditorName;

  @Positive
  @Column(name = "amount")
  private Long amount;

  @Column(name = "currency")
  private String currency;

}
