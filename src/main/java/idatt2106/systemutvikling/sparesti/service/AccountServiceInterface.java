package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;

import java.util.List;

public interface AccountServiceInterface {
  AccountDAO findAccountByAccountNr(Long accountNr);

  List<AccountDAO> findAccountsByUsername(String username);

  List<Long> findAccountsNumbersByUsername(String username);
}
