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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transactionTitle")
  private Long transactionId;

  @ManyToOne
  @JoinColumn(name = "accountNr")
  private AccountDAO accountDAO;

  @Column(name = "transactionTitle")
  private String transactionTitle;

  @Column(name = "time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date time;

  @NotNull
  @Column(name = "debtorAccount")
  private Long debtorAccount;

  @Column(name = "debtorName")
  private String debtorName;

  @NotNull
  @Column(name = "creditorAccount")
  private Long creditorAccount;

  @Column(name = "creditorName")
  private String creditorName;

  @Positive
  @Column(name = "amount")
  private Long amount;

  @Column(name = "currency")
  private String currency;



}
