package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_category")
public class TransactionCategoryDAO {
  @Id
  private Long transactionId;

  @Enumerated(EnumType.STRING)
  private TransactionCategory transactionCategory;

}
