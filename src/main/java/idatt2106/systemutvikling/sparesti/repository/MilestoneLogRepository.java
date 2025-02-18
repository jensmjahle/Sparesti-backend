package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the MilestoneLogDAO entity.
 */
@Repository
public interface MilestoneLogRepository extends JpaRepository<MilestoneLogDAO, Long> {

  /**
   * Method to find a milestone log by its id
   *
   * @param id the id of the milestone log
   * @return the milestone log with the given id
   */
  MilestoneLogDAO findMilestoneLogDAOByMilestoneId(Long id);

  /**
   * Method to find a milestone log by its id and username
   *
   * @param id       the id of the milestone log
   * @param username the username of the user
   * @return the milestone log with the given id and username
   */
  MilestoneLogDAO findMilestoneLogDAOByMilestoneIdAndUserDAO_Username(Long id, String username);

  /**
   * Method to find all milestone logs by username
   *
   * @param username the username of the user
   * @return a list of milestone logs that belong to the user with the given username
   */
  Page<MilestoneLogDAO> findMilestoneLogDAOByUserDAO_Username(String username, Pageable pageable);

  List<MilestoneLogDAO> findMilestoneLogDAOByUserDAO_Username(String username);

  /**
   * Method to find all milestone logs that have a completion date after the given completion date
   *
   * @param completionDate the completion date
   * @return a list of milestone logs that have a completion date after the given date
   */
  Page<MilestoneLogDAO> findMilestoneLogDAOSByCompletionDate(LocalDateTime completionDate,
      Pageable pageable);

  /**
   * Method to find all milestone logs that have a completion date before the given completion date
   *
   * @param completionDate the completion date
   * @return a list of milestone logs that have a completion date before the given date
   */
  List<MilestoneLogDAO> findMilestoneLogDAOSByCompletionDateAfter(LocalDateTime completionDate);

  /**
   * Method to find all milestone logs that have a completion date before the given completion date
   *
   * @param completionDate the completion date
   * @return a list of milestone logs that have a completion date before the given date
   */
  List<MilestoneLogDAO> findMilestoneLogDAOSByCompletionDateBefore(LocalDateTime completionDate);

  /**
   * Method to delete all milestone logs to a user.
   *
   * @param username the username of the user
   */
  void deleteAllByUserDAO_Username(String username);
}
