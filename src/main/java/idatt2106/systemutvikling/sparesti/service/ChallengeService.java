package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChallengeService {

  private final Logger logger = Logger.getLogger(ChallengeService.class.getName());
  private final ChallengeRepository challengeRepository;

  /**
   * Method to get all challenges by username
   *
   * @param username the username of the user
   * @param active   whether the challenges are active or not
   * @return a list of challenges that belong to the user with the given username
   */
  public List<ChallengeDAO> getChallengesByActiveAndUsername(String username, boolean active) {
    try {
      return challengeRepository.findChallengeDAOByActiveAndUserDAO_Username(active, username);
    } catch (Exception e) {
      logger.severe("Failed to get active challenges" + e.getMessage());
      return null;
    }
  }

  public List<ChallengeDAO> getChallengesStartedAfterDate(LocalDateTime startDate,
      String username) {
    try {
      return challengeRepository.findChallengeDAOSByStartDateAfterAndUserDAO_Username(startDate,
          username);
    } catch (Exception e) {
      logger.severe("Failed to get challenges this month for user " + username + e.getMessage());
      throw new RuntimeException(
          "Failed to get challenges this month for user " + username + e.getMessage());
    }
  }

}
