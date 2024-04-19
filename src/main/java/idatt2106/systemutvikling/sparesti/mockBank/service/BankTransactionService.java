package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.TransactionDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankTransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  @Autowired
  public BankTransactionService(TransactionRepository transactionRepository, AccountService accountService) {
    this.transactionRepository = transactionRepository;
    this.accountService = accountService;
  }

  public List<TransactionDAO> findTransactionsByAccountNr(Long accountNr) {
    AccountDAO account = accountService.findAccountByAccountNr(accountNr);
    return transactionRepository.findTransactionDAOSByAccountDAO(account);
  }
}
