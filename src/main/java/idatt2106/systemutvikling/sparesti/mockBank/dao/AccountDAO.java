package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AccountDAO {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountIdGenerator")
  @SequenceGenerator(name = "accountIdGenerator", sequenceName = "account_sequence",
      allocationSize = 1, initialValue = 12313123)
  private Long accountNr;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "username", referencedColumnName = "username")
  private CustomerDAO customerDAO;

  @Column(name = "balance")
  private Long balance;

  @Column(name = "accountName")
  private String name;

  @Column(name = "accountType")
  private String type;

  @Column(name = "currency")
  private String currency;
}
