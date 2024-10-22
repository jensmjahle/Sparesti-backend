package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the ChallengeLogDAO entity.
 */
@Repository
public interface ChallengeLogRepository extends JpaRepository<ChallengeLogDAO, Long> {

  /**
   * Method to find a challenge log by its id
   *
   * @param id the id of the challenge log
   * @return the challenge log with the given id
   */
  ChallengeLogDAO findChallengeLogDAOByChallengeId(Long id);

  /**
   * Method to find a challenge log by its id and username
   *
   * @param id       the id of the challenge log
   * @param username the username of the user
   * @return the challenge log with the given id and username
   */
  ChallengeLogDAO findChallengeLogDAOByChallengeIdAndUserDAO_Username(Long id, String username);

  /**
   * Method to find all challenge logs by username
   *
   * @param username the username of the user
   * @return a list of challenge logs that belong to the user with the given username
   */
  List<ChallengeLogDAO> findChallengeLogDAOSByUserDAO_Username(String username);

  /**
   * Method to find all challenge logs that have a completion date after the given completion date
   *
   * @param completionDate the completion date
   * @return a list of challenge logs that have a completion date after the given date
   */
  List<ChallengeLogDAO> findChallengeLogDAOSByCompletionDate(LocalDateTime completionDate);

  /**
   * Method to find all challenge logs that have a completion date before the given completion date
   *
   * @param completionDate the completion date
   * @return a list of challenge logs that have a completion date before the given date
   */
  List<ChallengeLogDAO> findChallengeLogDAOSByCompletionDateAfter(LocalDateTime completionDate);

  /**
   * Method to find all challenge logs that have a completion date after the given completion date
   * and belong to a user with the given username
   *
   * @param startDate the start date
   * @param username  the username of the user
   * @return a list of challenge logs that have a completion date after the given date and belong to
   * the user with the given username
   */
  List<ChallengeLogDAO> findChallengeLogDAOSByCompletionDateAfterAndUserDAO_Username(
      LocalDateTime startDate, String username);

  /**
   * Method to find all challenge logs that have a completion date before the given completion date
   *
   * @param completionDate the completion date
   * @return a list of challenge logs that have a completion date before the given date
   */
  List<ChallengeLogDAO> findChallengeLogDAOSByCompletionDateBefore(LocalDateTime completionDate);

  List<ChallengeLogDAO> findChallengeLogDAOByUserDAO_Username(String username);


  void deleteAllByUserDAO_Username(String username);
}
