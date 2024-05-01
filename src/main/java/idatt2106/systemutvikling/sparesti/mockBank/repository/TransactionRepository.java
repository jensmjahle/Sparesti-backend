package idatt2106.systemutvikling.sparesti.mockBank.repository;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;

/**
 * Repository interface for the Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionDAO, Long> {

  /**
   * Method to find all transactions that belong to an account.
   *
   * @param accountDAO the account entity
   * @return a list of transaction entities as TransactionDAO
   */
  List<TransactionDAO> findTransactionDAOSByAccountDAO(AccountDAO accountDAO);

  /**
   * Method to find all transactions that belong to an account with a given account number.
   *
   * @param debtorAccountNumber the account number of the account
   * @param pageable the page number
   * @return a list of transaction entities as TransactionDAO
   */
  List<TransactionDAO> findTransactionDAOByDebtorAccount(Long debtorAccountNumber, Pageable pageable);

  /**
   * Method to find all transactions that belong to an account with a given account number and a date limit.
   *
   * @param accountNr the account number of the account
   * @param dateLimit the date limit
   * @return a list of transaction entities as TransactionDAO
   */
  List<TransactionDAO> findByAccountDAO_AccountNrAndTimeAfter(Long accountNr, Date dateLimit);
}
