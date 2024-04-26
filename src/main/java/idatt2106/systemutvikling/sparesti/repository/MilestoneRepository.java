package idatt2106.systemutvikling.sparesti.repository;

import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MilestoneRepository extends JpaRepository<MilestoneDAO, Long> {

  /**
   * Method to find a milestone by its id
   *
   * @param id the id of the milestone
   * @return the milestone with the given id
   */
  MilestoneDAO findMilestoneDAOByMilestoneId(Long id);

  /**
   * Method to find a milestone by its id and username
   *
   * @param id       the id of the milestone
   * @param username the username of the user
   * @return the milestone with the given id and username
   */
  MilestoneDAO findMilestoneDAOByMilestoneIdAndUserDAO_Username(Long id, String username);

  /**
   * Method to find all milestones by username
   *
   * @param username the username of the user
   * @return a list of milestones that belong to the user with the given username
   */
  Page<MilestoneDAO> findMilestoneDAOByUserDAO_Username(String username, Pageable pageable);

  List<MilestoneDAO> findMilestoneDAOByUserDAO_Username(String username);


  /**
   * Method to find all milestones that have a start date after the given start date and a deadline date before the given deadline date
   *
   * @param startDate    the start date
   * @param deadlineDate the deadline date
   * @return a list of milestones that have a start date after the given date
   */
  List<MilestoneDAO> findMilestoneDAOSByStartDateAfterAndDeadlineDateBefore(LocalDateTime startDate, LocalDateTime deadlineDate);

  /**
   * Method to find all milestones that have a start date after the given start date
   *
   * @param startDate the start date
   * @return a list of milestones that have a start date after the given date
   */
  List<MilestoneDAO> findMilestoneDAOSByStartDateAfter(LocalDateTime startDate);

  /**
   * Method to find all milestones that have a deadline date before the given deadline date
   *
   * @param deadlineDate the deadline date
   * @return a list of milestones that have a deadline date before the given date
   */
  List<MilestoneDAO> findMilestoneDAOSByDeadlineDateBefore(LocalDateTime deadlineDate);


}
