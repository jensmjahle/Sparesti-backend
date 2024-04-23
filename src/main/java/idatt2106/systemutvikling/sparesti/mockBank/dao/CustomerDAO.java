package idatt2106.systemutvikling.sparesti.mockBank.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerDAO {
  @Id
  @NotNull
  @Column(name = "username")
  private String username;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;
}
