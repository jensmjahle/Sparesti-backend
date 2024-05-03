package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data access object for the Customer entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mock_customer")
public class CustomerDAO {

  @Id
  @NotNull
  @Column(name = "username")
  private String username;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
}
