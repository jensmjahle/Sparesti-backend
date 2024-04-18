package idatt2106.systemutvikling.sparesti.mockBank.repository;

import idatt2106.systemutvikling.sparesti.mockBank.dao.AccountDAO;
import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDAO, Long> {
  //Optional: Add additional methods
  //AccountDAO findAccountDAOByCustomerDAO_Username();
  Optional<AccountDAO> findAccountDAOByAccountNr(Long accountNr);
  List<AccountDAO> findAccountDAOSByCustomerDAO(CustomerDAO customer);
}
