package idatt2106.systemutvikling.sparesti.mockBank.repository;

import idatt2106.systemutvikling.sparesti.mockBank.dao.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO, String> {

}
