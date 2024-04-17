package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TransactionCategoryDAO {
  @Id
  private Long transactionId;
  @Enumerated(EnumType.STRING)

  private TransactionCategory transactionCategory;

}
