package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryCacheRepository extends
    JpaRepository<TransactionCategoryDAO, Long> {

  /**
   * Method to delete all transaction categories that were created before a given date
   *
   * @param createdAt the date to compare against
   */
  void deleteAllByCreatedAtBefore(Date createdAt);
}
