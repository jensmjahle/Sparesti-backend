package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.repository.TransactionCategoryCacheRepository;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for handling the cache of transaction categories.
 */
@Service
@AllArgsConstructor
public class TransactionCategoryCacheService {

  private final TransactionCategoryCacheRepository categoryCache;

  /**
   * Method to set a category in the cache.
   *
   * @param transactionId the id of the transaction
   * @param category      the category to set
   * @param createdAt     the time the category was set
   * @return the saved TransactionCategoryDAO
   */
  public TransactionCategoryDAO setCategoryCache(Long transactionId, TransactionCategory category,
      Date createdAt) {
    TransactionCategoryDAO dao = new TransactionCategoryDAO(transactionId, category, createdAt);
    return setCategoryCache(dao);
  }

  /**
   * Method to set a category in the cache.
   *
   * @param dao the TransactionCategoryDAO to save
   * @return the saved TransactionCategoryDAO
   */
  public TransactionCategoryDAO setCategoryCache(TransactionCategoryDAO dao) {
    return categoryCache.save(dao);
  }

  /**
   * Method to get a category from the cache.
   *
   * @param transactionId the id of the transaction
   * @return the TransactionCategoryDAO if found, null otherwise
   */
  public TransactionCategoryDAO getCategoryFromCache(Long transactionId) {
    return categoryCache.findById(transactionId).orElse(null);
  }

  /**
   * Method to delete a category from the cache.
   *
   * @param time the time to delete outdated cache
   */
  public void deleteOutdatedCache(Date time) {
    categoryCache.deleteAllByCreatedAtBefore(time);
  }
}
