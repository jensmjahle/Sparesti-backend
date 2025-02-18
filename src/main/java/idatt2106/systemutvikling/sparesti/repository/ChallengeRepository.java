package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the ChallengeDAO entity.
 */
@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeDAO, Long> {

  /**
   * Method to find a challenge by its id
   *
   * @param id the id of the challenge
   * @return the challenge with the given id
   */
  ChallengeDAO findChallengeDAOByChallengeId(Long id);

  /**
   * Method to find a challenge by its id and username
   *
   * @param id       the id of the challenge
   * @param username the username of the user
   * @return the challenge with the given id and username
   */
  ChallengeDAO findChallengeDAOByChallengeIdAndUserDAO_Username(Long id, String username);

  /**
   * Method to find all challenges by username
   *
   * @param username the username of the user
   * @return a list of challenges that belong to the user with the given username
   */
  List<ChallengeDAO> findChallengeDAOSByUserDAO_Username(String username, Pageable pageable);

  List<ChallengeDAO> findChallengeDAOSByUserDAO_Username(String username);

  Page<ChallengeDAO> findChallengeDAOSByUserDAO_UsernameAndActive(String username, boolean active,
      Pageable pageable);

  /**
   * Method to find all challenges that have a start date after the given start date and an
   * expiration date before the given expiration date
   *
   * @param startDate      the start date
   * @param expirationDate the expiration date
   * @return a list of challenges that have a start date after the given date
   */
  List<ChallengeDAO> findChallengeDAOSByStartDateAfterAndExpirationDateBefore(
      LocalDateTime startDate, LocalDateTime expirationDate);


  /**
   * Method to find all challenges that have a start date after the given start date
   *
   * @param startDate the start date
   * @return a list of challenges that have a start date after the given date
   */
  List<ChallengeDAO> findChallengeDAOSByStartDateAfter(LocalDateTime startDate);

  /**
   * Method to find all challenges that have a start date after the given start date and belong to a
   * user with the given username
   *
   * @param startDate the start date
   * @param username  the username of the user
   * @return a list of challenges that have a start date after the given date and belong to the user
   * with the given username
   */
  List<ChallengeDAO> findChallengeDAOSByStartDateAfterAndUserDAO_Username(LocalDateTime startDate,
      String username);

  /**
   * Method to find all challenges that have an expiration date before the given expiration date
   *
   * @param expirationDate the expiration date
   * @return a list of challenges that have an expiration date before the given date
   */
  List<ChallengeDAO> findChallengeDAOSByExpirationDateBefore(LocalDateTime expirationDate);

  /**
   * Method to find all challenges that have a given recurring interval
   *
   * @param recurringInterval the recurring interval
   * @return a list of challenges that have the given recurring interval
   */
  List<ChallengeDAO> findChallengeDAOSByRecurringInterval(RecurringInterval recurringInterval);

  /**
   * Method to find all challenges that are active
   *
   * @param active boolean value that indicates if the challenge is active
   * @return a list of challenges that are active
   */
  List<ChallengeDAO> findAllByActive(boolean active);

  //@Query(value = "SELECT * FROM challengedao cd WHERE cd.challenge_id = :id AND username = :username", nativeQuery = true)
  //ChallengeDAO findChallengeDAOByIdAndUsername(Long id, String username);


  /**
   * Method to find all challenges that are active and belong to a user with the given username
   *
   * @param active   boolean value that indicates if the challenge is active
   * @param username the username of the user
   * @return a list of challenges that are active and belong to the user with the given username
   */
  List<ChallengeDAO> findChallengeDAOByActiveAndUserDAO_Username(boolean active, String username);


  void deleteAllByUserDAO_Username(String username);


  ChallengeDAO findByChallengeIdAndUserDAO_Username(Long id, String username);
}
