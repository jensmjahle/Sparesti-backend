package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.repository.TransactionCategoryCacheRepository;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionCategoryCacheService {

  private final TransactionCategoryCacheRepository categoryCache;


  public TransactionCategoryDAO setCategoryCache(Long transactionId, TransactionCategory category,
      Date createdAt) {
    TransactionCategoryDAO dao = new TransactionCategoryDAO(transactionId, category, createdAt);
    return setCategoryCache(dao);
  }

  public TransactionCategoryDAO setCategoryCache(TransactionCategoryDAO dao) {
    return categoryCache.save(dao);
  }

  public TransactionCategoryDAO getCategoryFromCache(Long transactionId) {
    return categoryCache.findById(transactionId).orElse(null);
  }

  public void deleteOutdatedCache(Date time) {
    categoryCache.deleteAllByCreatedAtBefore(time);
  }
}
