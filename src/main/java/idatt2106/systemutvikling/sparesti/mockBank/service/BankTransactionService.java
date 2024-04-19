package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import idatt2106.systemutvikling.sparesti.service.TransactionServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService implements TransactionServiceInterface {

  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  @Override
  public List<TransactionDAO> getLatestExpensesForAccountNumber(Long accountNumber, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("time").ascending());

    return transactionRepository.findTransactionDAOByDebtorAccount(accountNumber, pageable);
  }

  public List<TransactionDAO> findTransactionsByAccountNr(Long accountNr) {
    AccountDAO account = accountService.findAccountByAccountNr(accountNr);
    return transactionRepository.findTransactionDAOSByAccountDAO(account);
  }
}
