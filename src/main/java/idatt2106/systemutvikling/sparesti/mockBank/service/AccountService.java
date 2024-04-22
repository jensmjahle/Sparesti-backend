package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.AccountRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
  private final AccountRepository accountRepository;
  private final CustomerService customerService;

  @Autowired
  public AccountService(AccountRepository accountRepository, CustomerService customerService) {
    this.accountRepository = accountRepository;
    this.customerService = customerService;
  }

  public AccountDAO findAccountByAccountNr(Long accountNumber) {
    Optional<AccountDAO> accountDAOOptional = accountRepository.findAccountDAOByAccountNr(accountNumber);
    return accountDAOOptional.orElseThrow(() -> new RuntimeException(accountNumber+" was not found"));
  }
  public List<AccountDAO> findAccountsByUsername(String username) {
    CustomerDAO customer = customerService.findCustomerByUsername(username);
    return accountRepository.findAccountDAOSByCustomerDAO(customer);
  }

  public List<AccountDAO> findOtherAccountsOwnedBySameUser(@NonNull Long accountNumber) {
    AccountDAO originalAccount = accountRepository.findAccountDAOByAccountNr(accountNumber).orElse(null);
    if (originalAccount == null)
      return null;

    CustomerDAO c = customerService.findCustomerByUsername(originalAccount.getCustomerDAO().getUsername());
    if (c == null)
      return null;

    return accountRepository.findAccountDAOSByCustomerDAO(c);
  }
}
