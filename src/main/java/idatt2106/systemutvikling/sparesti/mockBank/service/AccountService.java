package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import idatt2106.systemutvikling.sparesti.service.AccountServiceInterface;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface{
  private final AccountRepository accountRepository;
  private final CustomerService customerService;

  @Autowired
  public AccountService(AccountRepository accountRepository, CustomerService customerService) {
    this.accountRepository = accountRepository;
    this.customerService = customerService;
  }

  /**
   * Method to find an account by its account number, then return the account entity as AccountDAO.
   *
   * @param accountNumber the account number of the account
   * @return the account entity as AccountDAO
   */
  public AccountDAO findAccountByAccountNr(Long accountNumber) {
    Optional<AccountDAO> accountDAOOptional = accountRepository.findAccountDAOByAccountNr(accountNumber);
    return accountDAOOptional.orElseThrow(() -> new RuntimeException(accountNumber+" was not found"));
  }

  /**
   * Method to find all accounts that belong to a customer with a given username.
   *
   * @param username the username of the customer
   * @return a list of account entities as AccountDAO
   */
  public List<AccountDAO> findAccountsByUsername(String username) {
    CustomerDAO customer = customerService.findCustomerByUsername(username);
    return accountRepository.findAccountDAOSByCustomerDAO(customer);
  }

  /**
   * Method to find all account numbers that belong to a customer with a given username.
   *
   * @param username the username of the customer
   * @return a list of account numbers
   */
  public List<Long> findAccountsNumbersByUsername(String username) {
    List<AccountDAO> accounts = findAccountsByUsername(username);
    return accounts.stream().map(AccountDAO::getAccountNr).toList();
  }
}
