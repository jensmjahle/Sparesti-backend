package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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

  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private CustomerDAO customerDAO;


}
