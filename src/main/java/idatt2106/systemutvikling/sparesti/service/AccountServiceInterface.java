package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;

import java.util.List;

/**
 * Interface for the AccountService class.
 */
public interface AccountServiceInterface {

  /**
   * Method to find an account by the account number. The method finds the account based on the account number
   *
   * @param accountNr the account number of the account
   * @return the account with the account number
   */
  AccountDAO findAccountByAccountNr(Long accountNr);

  /**
   * Method to find accounts by the username. The method finds the accounts based on the username
   * and returns a list of accounts.
   *
   * @param username the username of the account
   * @return the account with the account number
   */
  List<AccountDAO> findAccountsByUsername(String username);

  /**
   * Method to find an account by the account number. The method finds the account based on the account number
   *
   * @param username the username of the account
   * @return the account with the account number
   */
  List<Long> findAccountsNumbersByUsername(String username);
}
