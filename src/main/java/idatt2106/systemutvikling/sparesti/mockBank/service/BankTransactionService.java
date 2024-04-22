package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.model.Transaction;
import idatt2106.systemutvikling.sparesti.service.AchievementService;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class BankTransactionService implements TransactionServiceInterface {

  private final Logger logger = Logger.getLogger(BankTransactionService.class.getName());
  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  @Override
  public List<Transaction> getLatestExpensesForAccountNumber(@NonNull Long accountNumber, @Positive int page, @Positive int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("time").ascending());

    return transactionRepository
            .findTransactionDAOByDebtorAccount(accountNumber, pageable)
            .stream()
            .map(BankTransactionService::toModel)
            .toList();
  }

  /**
   * Creates a transaction and saves it to the database.
   *
   * @param debtorName The name of the debtor.
   * @param creditorName The name of the creditor.
   * @param transactionTitle The title of the transaction.
   * @param debtorAccount The account number of the debtor.
   * @param creditorAccount The account number of the creditor.
   * @param amount The amount of the transaction.
   * @param currency The currency of the transaction.
   * @return TransactionDAO The saved transaction DAO.
   */
  public Boolean createTransaction(String debtorName, String creditorName, String transactionTitle, Long debtorAccount, Long creditorAccount, Long amount, String currency) {
    try {
      TransactionDAO transactionDAO = new TransactionDAO();

      transactionDAO.setAccountDAO(accountService.findAccountByAccountNr(debtorAccount));
      transactionDAO.setTransactionTitle(transactionTitle);
      transactionDAO.setTime(new Date());
      transactionDAO.setDebtorAccount(debtorAccount);
      transactionDAO.setDebtorName(debtorName);
      transactionDAO.setCreditorAccount(creditorAccount);
      transactionDAO.setCreditorName(creditorName);
      transactionDAO.setAmount(amount);
      transactionDAO.setCurrency(currency);

      transactionRepository.save(transactionDAO);
      return true;
    } catch (Exception e) {
      logger.severe("Something went wrong when making transaction");
      return false;
    }
  }

  public List<TransactionDAO> findTransactionsByAccountNr(Long accountNr) {
    AccountDAO account = accountService.findAccountByAccountNr(accountNr);
    return transactionRepository.findTransactionDAOSByAccountDAO(account);
  }
  public static Transaction toModel(TransactionDAO dao) {
    return new Transaction(
        dao.getTransactionId(),
        dao.getAccountDAO().getAccountNr(),
        dao.getTransactionTitle(),
        dao.getTime(),
        dao.getDebtorAccount(),
        dao.getDebtorName(),
        dao.getCreditorAccount(),
        dao.getCreditorName(),
        dao.getAmount(),
        dao.getCurrency()
    );
  }
}
