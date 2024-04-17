package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
  private final AccountRepository accountRepository;
  private final CustomerService customerService;

  @Autowired
  public AccountService(AccountRepository accountRepository, CustomerService customerService) {
    this.accountRepository = accountRepository;
    this.customerService = customerService;
  }

  public List<AccountDAO> findAccountsByUsername(String username) {
    CustomerDAO customer = customerService.findCustomerByUsername(username);
    return accountRepository.findAccountDAOSByCustomerDAO(customer);
  }
}
