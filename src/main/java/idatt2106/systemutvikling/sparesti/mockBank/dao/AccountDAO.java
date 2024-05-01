package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data access object for the Account entity.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "mock_account")
public class AccountDAO {

  @Id
  @NotNull
  @Column(name = "account_nr")
  private Long accountNr;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "username", referencedColumnName = "username")
  private CustomerDAO customerDAO;

  @Column(name = "balance")
  private Long balance;

  @Column(name = "accountName")
  private String name;

  @Column(name = "account_type")
  private String type;

  @Column(name = "currency")
  private String currency;
}
