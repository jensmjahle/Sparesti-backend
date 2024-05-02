package idatt2106.systemutvikling.sparesti.mockBank.repository;

import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for the Customer entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO, String> {

  /**
   * Method to find a customer by its username.
   *
   * @param username the username of the customer
   * @return the customer entity as CustomerDAO
   */
  Optional<CustomerDAO> findByUsername(String username);
}
