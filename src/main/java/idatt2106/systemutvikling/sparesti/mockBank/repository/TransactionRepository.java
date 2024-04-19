package idatt2106.systemutvikling.sparesti.mockBank.repository;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDAO, Long> {
  List<TransactionDAO> findTransactionDAOSByAccountDAO(AccountDAO accountDAO);

  List<TransactionDAO> findTransactionDAOByDebtorAccount(Long debtorAccountNumber, Pageable pageable);
}
