package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.mapper.MockBankAccountMapper;
import idatt2106.systemutvikling.sparesti.mockBank.repository.AccountRepository;
import idatt2106.systemutvikling.sparesti.model.BankAccount;
import idatt2106.systemutvikling.sparesti.model.PSUConsent;
import idatt2106.systemutvikling.sparesti.service.BankAccountServiceInterface;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for the BankAccount entity.
 */
@Service
public class MockBankAccountService implements BankAccountServiceInterface {
  private final AccountRepository accountRepository;
  private final CustomerService customerService;

  @Autowired
  public MockBankAccountService(AccountRepository accountRepository, CustomerService customerService) {
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
   * @param accountNumber the account number of the account
   * @return a list of account numbers
   */
  public List<AccountDAO> findOtherAccountsOwnedBySameUser(@NonNull Long accountNumber) {
    AccountDAO originalAccount = accountRepository.findAccountDAOByAccountNr(accountNumber).orElse(null);
    if (originalAccount == null)
      return null;

    CustomerDAO c = customerService.findCustomerByUsername(originalAccount.getCustomerDAO().getUsername());
    if (c == null)
      return null;

    return accountRepository.findAccountDAOSByCustomerDAO(c);
  }

  /**
   * Method to find all account numbers that belong to a customer with a given username.
   *
   * @param consent the consent of the PSU user
   * @return a list of account numbers
   */
  public List<BankAccount> getAllAccountsOfUser(PSUConsent consent) {
    String username = consent.getPsuId();

    List<AccountDAO> daos = accountRepository.findByCustomerDAO_Username(username);
    if (daos == null || daos.isEmpty())
      return null;

    return daos.stream()
            .map(MockBankAccountMapper::toModel)
            .toList();
  }
}
