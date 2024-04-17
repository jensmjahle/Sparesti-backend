package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerDAO {
  @Id
  @NotNull
  private String username;

  private String firstName;

  private String lastName;

}
