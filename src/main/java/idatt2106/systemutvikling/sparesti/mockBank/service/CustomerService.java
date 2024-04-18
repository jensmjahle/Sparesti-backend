package idatt2106.systemutvikling.sparesti.mockBank.service;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import idatt2106.systemutvikling.sparesti.mockBank.repository.AccountRepository;
import idatt2106.systemutvikling.sparesti.mockBank.repository.CustomerRepository;
import idatt2106.systemutvikling.sparesti.service.CustomerServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService implements CustomerServiceInterface {

  private final CustomerRepository customerRepository;
  private final AccountRepository accountRepository;

  @Override
  public boolean customerExists(String username) {
    return findCustomerByUsername(username) != null;
  }

  @Override
  public boolean hasTwoAccounts(String username) {
    Optional<CustomerDAO> customerOpt = customerRepository.findByUsername(username);

    if (customerOpt.isEmpty())
      return false;

    List<AccountDAO> accounts = accountRepository.findAccountDAOSByCustomerDAO(customerOpt.get());

    return accounts.size() >= 2;
  }

  public CustomerDAO findCustomerByUsername(String username) {
    Optional<CustomerDAO> customerDAOOptional = customerRepository.findByUsername(username);
    return customerDAOOptional.orElseThrow(() -> new RuntimeException(username+" was not found"));
  }


}