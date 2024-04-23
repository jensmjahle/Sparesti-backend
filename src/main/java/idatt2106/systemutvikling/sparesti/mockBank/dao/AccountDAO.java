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
  @Column(name = "accountNr")
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
