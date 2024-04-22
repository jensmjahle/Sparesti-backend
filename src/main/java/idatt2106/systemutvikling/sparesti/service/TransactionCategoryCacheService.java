package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import idatt2106.systemutvikling.sparesti.enums.TransactionCategory;
import idatt2106.systemutvikling.sparesti.repository.TransactionCategoryCacheRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionCategoryCacheService {

    private final TransactionCategoryCacheRepository categoryCache;



    public void setCategoryCache(Long transactionId, TransactionCategory category) {
        TransactionCategoryDAO dao = new TransactionCategoryDAO(transactionId, category);
        setCategoryCache(dao);
    }

    public void setCategoryCache(TransactionCategoryDAO dao) {
        categoryCache.save(dao);
    }

    public TransactionCategoryDAO getCategoryFromCache(Long transactionId) {
        return categoryCache.findById(transactionId).orElse(null);
    }
}