package idatt2106.systemutvikling.sparesti.mockBank.repository;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Account entity.
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountDAO, Long> {

  /**
   * Method to find an account by its account number. Returns the account entity as AccountDAO.
   *
   * @param accountNr the account number of the account
   * @return the account entity as AccountDAO
   */
  Optional<AccountDAO> findAccountDAOByAccountNr(Long accountNr);

  /**
   * Method to find all accounts that belong to a customer.
   *
   * @param customer the customer entity
   * @return a list of account entities as AccountDAO
   */
  List<AccountDAO> findAccountDAOSByCustomerDAO(CustomerDAO customer);

  /**
   * Method to find all accounts that belong to a customer with a given username.
   *
   * @param username the username of the customer
   * @return a list of account entities as AccountDAO
   */
  List<AccountDAO> findByCustomerDAO_Username(String username);
}
