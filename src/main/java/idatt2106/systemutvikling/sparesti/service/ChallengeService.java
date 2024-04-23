package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import java.util.List;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChallengeService {

  private final Logger logger = Logger.getLogger(ChallengeService.class.getName());
  private final ChallengeRepository challengeRepository;


  public List<ChallengeDAO> getChallengesByActiveAndUsername(String username, boolean active) {
    try {
      return challengeRepository.findChallengeDAOByActiveAndUserDAO_Username(active, username);
    } catch (Exception e) {
      logger.severe("Failed to get active challenges" + e.getMessage());
      return null;
    }
  }


}
