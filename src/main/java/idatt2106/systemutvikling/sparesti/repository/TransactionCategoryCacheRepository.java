package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.TransactionCategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryCacheRepository extends JpaRepository<TransactionCategoryDAO, Long> {
    
}
