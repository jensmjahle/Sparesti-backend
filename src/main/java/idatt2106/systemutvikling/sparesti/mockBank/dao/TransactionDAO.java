package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.*;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transactionIdGenerator")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "accountNr")
  private AccountDAO accountDAO;

  private String transactionTitle;

  @Temporal(TemporalType.TIMESTAMP)
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
