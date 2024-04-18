package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, String> {

  /**
   * Method to find a user by its username
   *
   * @param username the username of the user
   * @return the user entity as UserDAO
   */
  UserDAO findByUsername(String username);
}