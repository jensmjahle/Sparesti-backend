package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChallengeService {

  private final Logger logger = Logger.getLogger(ChallengeService.class.getName());
  private ChallengeRepository challengeRepository;
  private ChallengeLogRepository challengeLogRepository;

  public ChallengeDTO getChallenge(Long challengeId) {
    return ChallengeMapper.toDTO(challengeRepository.findChallengeDAOByChallengeId(challengeId));
  }

  public List<ChallengeDTO> getActiveChallenges(String username, int page, int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("expirationDate").descending());
    hasChallengeTimeElapsed(
        challengeRepository.findChallengeDAOSByUserDAO_Username(username, pageable));

    List<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive(
        username, true, pageable);

    List<ChallengeDTO> challengeDTOS = new ArrayList<>();
    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }

    return challengeDTOS;
  }

  public List<ChallengeDTO> getInactiveChallenges(String username, int page, int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("expirationDate").descending());
    hasChallengeTimeElapsed(
        challengeRepository.findChallengeDAOSByUserDAO_Username(username, pageable));

    List<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive(
        username, false, pageable);

    List<ChallengeDTO> challengeDTOS = new ArrayList<>();
    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }

    return challengeDTOS;
  }

  public void hasChallengeTimeElapsed(List<ChallengeDAO> challengeDAOS) {
    LocalDateTime now = LocalDateTime.now();

    for (ChallengeDAO challengeDAO : challengeDAOS) {
      if (challengeDAO.getExpirationDate().isAfter(now)) {
        ChallengeLogDAO challengeLogDAO = createChallengeLog(challengeDAO);
        challengeLogRepository.save(challengeLogDAO);
        challengeRepository.delete(challengeDAO);
      }
    }
  }

  public ChallengeLogDAO createChallengeLog(ChallengeDAO challengeDAO) {
    ChallengeLogDAO challengeLogDAO = new ChallengeLogDAO();
    challengeLogDAO.setChallengeId(challengeDAO.getChallengeId());
    challengeLogDAO.setChallengeTitle(challengeDAO.getChallengeTitle());
    challengeLogDAO.setChallengeDescription(challengeDAO.getChallengeDescription());
    challengeLogDAO.setGoalSum(challengeDAO.getGoalSum());
    challengeLogDAO.setChallengeAchievedSum(challengeDAO.getCurrentSum());
    challengeLogDAO.setCompletionDate(LocalDateTime.now());
    challengeLogDAO.setUserDAO(challengeDAO.getUserDAO());
    return challengeLogDAO;
  }

  public ChallengeDAO createChallenge(ChallengeDTO challengeDTO) {
    return challengeRepository.save(ChallengeMapper.toDAO(challengeDTO));
  }

  public ChallengeDAO activateChallenge(Long challengeId) {
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);
    challengeDAO.setActive(true);
    return challengeRepository.save(challengeDAO);
  }

  public void completeChallenge(Long challengeId) {
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);
    ChallengeLogDAO challengeLogDAO = createChallengeLog(challengeDAO);
    challengeRepository.delete(challengeDAO);
    challengeLogRepository.save(challengeLogDAO);
  }

  public void deleteChallenge(Long challengeId) {
    challengeRepository.delete(challengeRepository.findChallengeDAOByChallengeId(challengeId));
  }

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
