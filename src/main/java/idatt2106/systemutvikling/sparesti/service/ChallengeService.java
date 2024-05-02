package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;

import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling challenges.
 */
@Service
@AllArgsConstructor
public class ChallengeService {

  private final Logger logger = Logger.getLogger(ChallengeService.class.getName());
  private ChallengeRepository challengeRepository;
  private ChallengeLogRepository challengeLogRepository;

  /**
   * Method to get a challenge by its id from the database and return it as a ChallengeDTO object to the frontend.
   *
   * @param challengeId the id of the challenge
   * @return the challenge with the given id
   */
  public ChallengeDTO getChallenge(Long challengeId) {
    return ChallengeMapper.toDTO(challengeRepository.findChallengeDAOByChallengeId(challengeId));
  }

  /**
   * Method to get all challenges from the database and return them as a list of ChallengeDTO objects to the frontend.
   *
   * @return a list of all challenges
   */
  public List<ChallengeDTO> getAllChallenges() {
    List<ChallengeDAO> challengeDAOS = challengeRepository.findAll();
    List<ChallengeDTO> challengeDTOS = new ArrayList<>();

    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }

    return challengeDTOS;
  }

  /**
   * Method to get all challenges by username. The challenges are sorted by expiration date. The method returns a list of ChallengeDTO objects to the frontend.
   *
   * @param username the username of the user
   * @param page tells which page to get
   * @param size tells how many challenges to get
   * @return a list of challenges that belong to the user with the given username
   */
  public List<ChallengeDTO> getChallengesByUsername(String username, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("expirationDate").descending());

    List<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_Username(
        username, pageable);
    List<ChallengeDTO> challengeDTOS = new ArrayList<>();

    for (int i = 0; i < challengeDAOS.size(); i++) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAOS.get(i)));
    }

    return challengeDTOS;
  }

  /**
    * Method to get all active challenges by username. The challenges are sorted by expiration date. The method returns a list of ChallengeDTO objects to the frontend.
   *
   * @param username the username of the user
   * @param pageable the pageable object
   * @return a list of challenges that belong to the user with the given username
   */
  public Page<ChallengeDTO> getActiveChallenges(String username, Pageable pageable) {

    Pageable sortedPageable = PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        Sort.by("expirationDate").ascending());

    hasChallengeTimeElapsed(
        challengeRepository.findChallengeDAOSByUserDAO_Username(username, sortedPageable));

    Page<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive(
        username, true, sortedPageable);

    List<ChallengeDTO> challengeDTOS = new ArrayList<>();

    for (int i = 0; i < challengeDAOS.getContent().size(); i++) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAOS.getContent().get(i)));
    }

    return new PageImpl<>(challengeDTOS, sortedPageable, challengeDAOS.getTotalElements());
  }

  /**
   * Method to get all inactive challenges by username. The challenges are sorted by expiration date. The method returns a list of ChallengeDTO objects to the frontend.
   *
   * @param username the username of the user
   * @param pageable the pageable object
   * @return a list of challenges that belong to the user with the given username
   */
  public Page<ChallengeDTO> getInactiveChallenges(String username, Pageable pageable) {

    Pageable sortedPageable = PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        Sort.by("expirationDate").ascending());

    hasChallengeTimeElapsed(
        challengeRepository.findChallengeDAOSByUserDAO_Username(username, pageable));

    Page<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive(
        username, false, pageable);

    List<ChallengeDTO> challengeDTOS = new ArrayList<>();
    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }

    return new PageImpl<>(challengeDTOS, sortedPageable, challengeDAOS.getTotalElements());
  }

  /**
   * Method to check if the time for a challenge has elapsed. If the time has elapsed, the challenge is saved as a challenge log and deleted from the database.
   *
   * @param challengeDAOS a list of challenges to check
   */
  public void hasChallengeTimeElapsed(List<ChallengeDAO> challengeDAOS) {
    LocalDateTime now = LocalDateTime.now();

    for (int i = 0; i < challengeDAOS.size(); i++) {
      if (!challengeDAOS.get(i).getExpirationDate().isAfter(now)) {
        ChallengeLogDAO challengeLogDAO = createChallengeLog(challengeDAOS.get(i));
        challengeLogRepository.save(challengeLogDAO);
        challengeRepository.delete(challengeDAOS.get(i));
      }
    }
  }

  /**
   * Method to create a challenge log from a challenge. The method returns a ChallengeLogDAO object.
   * The challenge log is created with the current date and time. The challenge log is created with the same information as the challenge.
   *
   * @param challengeDAO the challenge to create a challenge log from
   * @return the challenge log
   */
  public ChallengeLogDAO createChallengeLog(ChallengeDAO challengeDAO) {
    ChallengeLogDAO challengeLogDAO = new ChallengeLogDAO();
    challengeLogDAO.setChallengeId(challengeDAO.getChallengeId());
    challengeLogDAO.setChallengeTitle(challengeDAO.getChallengeTitle());
    challengeLogDAO.setChallengeDescription(challengeDAO.getChallengeDescription());
    challengeLogDAO.setGoalSum(challengeDAO.getGoalSum());
    challengeLogDAO.setChallengeAchievedSum(challengeDAO.getCurrentSum());
    challengeLogDAO.setCompletionDate(LocalDateTime.now());
    challengeLogDAO.setUserDAO(challengeDAO.getUserDAO());
    challengeLogDAO.setTheme(challengeDAO.getTheme());
    return challengeLogDAO;
  }

  /**
   * Method to create a challenge. The method returns a ChallengeDAO object. The challenge is created with the current date and time.
   * The challenge is created with the same information as the challengeDTO. The challenge is created with the current user as the owner.
   *
   * @param challengeDTO the challenge to create
   * @return the challenge
   */
  public ChallengeDAO createChallenge(ChallengeDTO challengeDTO) {
    challengeDTO.setActive(true);
    challengeDTO.setStartDate(LocalDateTime.now());
    challengeDTO.setCurrentSum(0L);
    challengeDTO.setUsername(CurrentUserService.getCurrentUsername());

    return challengeRepository.save(ChallengeMapper.toDAO(challengeDTO));
  }

  /**
   * Method to update a challenge. The method returns a ChallengeDAO object. The challenge is updated with the current date and time.
   * The challenge is updated with the same information as the challengeDTO. The challenge is updated with the current user as the owner.
   *
   * @param challengeId the id of the challenge to activate
   * @return the activated challenge
   */
  public ChallengeDAO activateChallenge(Long challengeId) {
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);
    challengeDAO.setActive(true);
    return challengeRepository.save(challengeDAO);
  }

  /**
   * Method to complete a challenge. The method returns a ChallengeLogDAO object. The challenge is completed with the current date and time.
   * The challenge is completed with the same information as the challenge. The challenge is deleted from the database and saved as a challenge log.
   *
   * @param challengeId the id of the challenge to complete
   * @return the completed challenge
   */
  public ChallengeLogDAO completeChallenge(Long challengeId) {
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);
    ChallengeLogDAO challengeLogDAO = createChallengeLog(challengeDAO);
    challengeLogDAO.setChallengeAchievedSum(challengeLogDAO.getGoalSum());
    challengeRepository.delete(challengeDAO);
    challengeLogRepository.save(challengeLogDAO);
    return challengeLogDAO;
  }

  /**
   * Method to move a challenge from active challenges database
   * to the complete challenge database.
   *
   * @param challengeId the id of the challenge to delete
   */
  public void moveChallengeToLog(Long challengeId) {
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);
    ChallengeLogDAO challengeLogDAO = createChallengeLog(challengeDAO);
    challengeRepository.delete(challengeRepository.findChallengeDAOByChallengeId(challengeId));
    challengeLogRepository.save(challengeLogDAO);
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

  /**
   * Method to get all challenges by username that have started after a given date and time and are active
   *
   * @param username the username of the user
   * @return a list of challenges that belong to the user with the given username
   */
  public List<ChallengeDAO> getChallengesStartedAfterDate(LocalDateTime startDate, String username) {
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
