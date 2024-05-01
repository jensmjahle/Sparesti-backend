package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.ChallengeLogDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.exceptions.BankConnectionErrorException;
import idatt2106.systemutvikling.sparesti.exceptions.NotFoundInDatabaseException;
import idatt2106.systemutvikling.sparesti.mapper.ChallengeMapper;
import idatt2106.systemutvikling.sparesti.repository.ChallengeLogRepository;
import idatt2106.systemutvikling.sparesti.repository.ChallengeRepository;

import java.util.logging.Logger;

import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChallengeService {

  private final Logger logger = Logger.getLogger(ChallengeService.class.getName());
  private final TransactionService transactionService;
  private final ChallengeRepository challengeRepository;
  private final ChallengeLogRepository challengeLogRepository;
  private final MilestoneService milestoneService;
  private final MilestoneRepository dbMilestone;

  public ChallengeDTO getChallenge(Long challengeId) {
    return ChallengeMapper.toDTO(challengeRepository.findChallengeDAOByChallengeId(challengeId));
  }

  public List<ChallengeDTO> getAllChallenges() {
    List<ChallengeDAO> challengeDAOS = challengeRepository.findAll();
    List<ChallengeDTO> challengeDTOS = new ArrayList<>();

    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }

    return challengeDTOS;
  }

  public List<ChallengeDTO> getChallengesByUsername(String username, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("expirationDate").descending());

    List<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_Username(
        username, pageable);
    List<ChallengeDTO> challengeDTOS = new ArrayList<>();

    /*
    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }
     */

    for (int i = 0; i < challengeDAOS.size(); i++) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAOS.get(i)));
    }

    return challengeDTOS;
  }

  public Page<ChallengeDTO> getActiveChallenges(String username, Pageable pageable) {

    Pageable sortedPageable = PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        Sort.by("expirationDate").descending());

    hasChallengeTimeElapsed(
        challengeRepository.findChallengeDAOSByUserDAO_Username(username, sortedPageable));

    Page<ChallengeDAO> challengeDAOS = challengeRepository.findChallengeDAOSByUserDAO_UsernameAndActive(
        username, true, sortedPageable);

    List<ChallengeDTO> challengeDTOS = new ArrayList<>();

    /*
    for (ChallengeDAO challengeDAO : challengeDAOS) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAO));
    }
     */

    for (int i = 0; i < challengeDAOS.getContent().size(); i++) {
      challengeDTOS.add(ChallengeMapper.toDTO(challengeDAOS.getContent().get(i)));
    }

    return new PageImpl<>(challengeDTOS, sortedPageable, challengeDAOS.getTotalElements());
  }

  public Page<ChallengeDTO> getInactiveChallenges(String username, Pageable pageable) {

    Pageable sortedPageable = PageRequest.of(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        Sort.by("expirationDate").descending());

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

  public ChallengeDAO createChallenge(ChallengeDTO challengeDTO) {
    challengeDTO.setActive(true);
    challengeDTO.setStartDate(LocalDateTime.now());
    challengeDTO.setCurrentSum(0L);
    challengeDTO.setUsername(CurrentUserService.getCurrentUsername());

    return challengeRepository.save(ChallengeMapper.toDAO(challengeDTO));
  }

  public ChallengeDAO activateChallenge(Long challengeId) {
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);
    challengeDAO.setActive(true);
    return challengeRepository.save(challengeDAO);
  }

  @Transactional
  public void completeChallengeForCurrentUser(long challengeId, long milestoneId) {
    // Fetch username from security context
    final String username = CurrentUserService.getCurrentUsername();

    // Fetch data
    ChallengeDAO challenge = challengeRepository.findByChallengeIdAndUserDAO_Username(challengeId, username);
    MilestoneDAO milestone = dbMilestone.findMilestoneDAOByMilestoneIdAndUserDAO_Username(milestoneId, username);

    // Verify existence of the requested challenge
    if (challenge == null)
      throw new NotFoundInDatabaseException("No challenge found with the requested ID for the user");

    // Verify existence of the requested milestone
    if (milestone == null)
      throw new NotFoundInDatabaseException("No milestone found with the requested ID for the user");



    // Define transfer amount as the difference between the goal and the current sum
    long transferAmount = challenge.getGoalSum() - challenge.getCurrentSum();

    // Perform savings transaction
    boolean success = transactionService.createSavingsTransferForCurrentUser(transferAmount);

    // Verify transaction success
    if (success)
      throw new BankConnectionErrorException("Failed to transfer funds to savings");


    // Transfer achieved currency to milestone
    MilestoneDAO savedEntry = milestoneService.increaseMilestonesCurrentSum(milestoneId, challenge.getGoalSum());


    // Archive challenge
    archiveActiveChallenge(challenge.getChallengeId());
  }

  public ChallengeLogDAO archiveActiveChallenge(Long challengeId) {
    // Fetch active challenge from database
    ChallengeDAO challengeDAO = challengeRepository.findChallengeDAOByChallengeId(challengeId);

    // Verify existence of the active challenge
    if (challengeDAO == null)
      return null;

    // Create log entry from the active challenge
    ChallengeLogDAO challengeLogDAO = createChallengeLog(challengeDAO);

    // Set the archived currency equal to the goal of the active challenge
    challengeLogDAO.setChallengeAchievedSum(challengeLogDAO.getGoalSum());

    // Save challenge log entry to database
    challengeLogDAO = challengeLogRepository.save(challengeLogDAO);

    // Remove active challenge from database
    challengeRepository.delete(challengeDAO);

    // Return the archived entry
    return challengeLogDAO;
  }

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
