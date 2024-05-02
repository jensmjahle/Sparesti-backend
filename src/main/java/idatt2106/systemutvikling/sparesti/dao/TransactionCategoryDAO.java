package idatt2106.systemutvikling.sparesti.dao;

import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Access Object for TransactionCategory
 */
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
  private Date createdAt;

}
