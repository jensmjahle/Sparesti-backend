package idatt2106.systemutvikling.sparesti.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserDAO {
@Id
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  @Lob
  private byte[] profilePicture;
  private Long monthlyIncome;
  private Long monthlySavings;
  private Long monthlyFixedExpenses;
  private Long currentAccount;
  private Long savingsAccount;

}
