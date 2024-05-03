package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ManualSavingDAO;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the ManualSavingDAO entity.
 */
public interface ManualSavingRepository extends JpaRepository<ManualSavingDAO, Long> {

  /**
   * Method to find a manual saving by its id and username of the user
   *
   * @param username  the username of the user
   * @param threshold the threshold date
   * @return a list of manual savings that belong to the user with the given username
   */
  List<ManualSavingDAO> findByUser_UsernameAndTimeOfTransferAfter(String username, Date threshold);

  /**
   * Method to delete all manual savings to a user.
   *
   * @param username the username of the user
   */
  void deleteAllByUser_Username(String username);
}
