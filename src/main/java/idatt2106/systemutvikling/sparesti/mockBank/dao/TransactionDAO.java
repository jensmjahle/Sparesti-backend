package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionDAO {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionIdGenerator")
  @SequenceGenerator(name = "transactionIdGenerator", sequenceName = "transaction_sequence",
      allocationSize = 1)
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "accountNr")
  private AccountDAO accountDAO;

  private String transactionTitle;

  private Date time;

  @NotNull
  private Long debtorAccount;

  private String debtorName;
  @NotNull
  private Long creditorAccount;

  private String creditorName;

  @Positive
  private Long amount;

  private String currency;



}
