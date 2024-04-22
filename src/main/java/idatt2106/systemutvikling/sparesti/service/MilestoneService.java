package idatt2106.systemutvikling.sparesti.service;


import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.mapper.MilestoneMapper;
import idatt2106.systemutvikling.sparesti.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;

import java.util.List;

@Service
public class MilestoneService {

  private final MilestoneRepository milestoneRepository;

  private final MilestoneLogRepository milestoneLogRepository;

  private final JWTService jwtService;

  private final UserRepository userRepository;

  private final Logger logger = Logger.getLogger(MilestoneService.class.getName());

  private final MilestoneLogService milestoneLogService;

  @Autowired
  public MilestoneService(MilestoneRepository milestoneRepository, JWTService jwtService, UserRepository userRepository, MilestoneLogRepository milestoneLogRepository, MilestoneLogService milestoneLogService) {
    this.milestoneRepository = milestoneRepository;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.milestoneLogRepository = milestoneLogRepository;
    this.milestoneLogService = milestoneLogService;
  }

  /**
   * Method to get all active milestones for a user.
   *
   * @param token The token of the user to get milestones for.
   * @return List of MilestoneDTOs.
   */
  public List<MilestoneDTO> getActiveMilestonesDTOsByUsername(String token) {
    String username = jwtService.extractUsernameFromToken(token);
    try {
      List<MilestoneDAO> milestoneDAOs = milestoneRepository.findMilestoneDAOByUserDAO_Username(username);
      List<MilestoneDTO> milestoneDTOS = null;
      for (MilestoneDAO milestoneDAO : milestoneDAOs) {
        milestoneDTOS.add(MilestoneMapper.toDTO(milestoneDAO));
      }
      return milestoneDTOS;
    } catch (Exception e) {
      logger.severe("Error when getting milestones: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to complete a milestone.
   *
   * @param token       token of the user to complete the milestone
   * @param milestoneId the id of the milestone to complete
   */
  public void completeMilestone(String token, Long milestoneId) {
    String username = jwtService.extractUsernameFromToken(token);
    try {
      MilestoneDAO milestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneIdAndUserDAO_Username(milestoneId, username);
      MilestoneLogDAO milestoneLogDAO = MilestoneMapper.toLogDAO(milestoneDAO);
      //milestoneLogRepository.save(milestoneLogDAO);
      milestoneLogService.completeMilestone(milestoneLogDAO);
      milestoneRepository.delete(milestoneDAO);
    } catch (Exception e) {
      logger.severe("Error when completing milestone: " + e.getMessage());
    }
  }

  /**
   * Method to create a milestone.
   *
   * @param token        token of the user to create the milestone
   * @param milestoneDTO the milestone to create
   * @return the created milestone
   */
  public MilestoneDTO createMilestoneDTO(String token, MilestoneDTO milestoneDTO) {
    String username = jwtService.extractUsernameFromToken(token);
    try {
      MilestoneDAO milestoneDAO = MilestoneMapper.toDAO(milestoneDTO);
      milestoneDAO.setUserDAO(userRepository.findByUsername(username));
      milestoneRepository.save(milestoneDAO);
      return MilestoneMapper.toDTO(milestoneDAO);
    } catch (Exception e) {
      logger.severe("Error when creating milestone: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to get a milestone by its id.
   *
   * @param token       token of the user to get the milestone
   * @param milestoneID the id of the milestone
   * @return the milestone
   */
  public MilestoneDTO getMilestoneDTOById(String token, Long milestoneID) {
    String username = jwtService.extractUsernameFromToken(token);
    try {
      MilestoneDAO milestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneIdAndUserDAO_Username(milestoneID, username);
      return MilestoneMapper.toDTO(milestoneDAO);
    } catch (Exception e) {
      logger.severe("Error when getting milestone: " + e.getMessage());
      return null;
    }
  }

  public MilestoneDTO updateMilestoneDTO(String token, MilestoneDTO milestoneDTO) {
    String username = jwtService.extractUsernameFromToken(token);
    try {
      MilestoneDAO updatingMilestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneIdAndUserDAO_Username(milestoneDTO.getMilestoneId(), username);
      updatingMilestoneDAO.setMilestoneCurrentSum(milestoneDTO.getMilestoneCurrentSum());

      if (updatingMilestoneDAO.getMilestoneCurrentSum() >= updatingMilestoneDAO.getMilestoneGoalSum()) {
        completeMilestone(token, updatingMilestoneDAO.getMilestoneId());
      } else {
        milestoneRepository.save(updatingMilestoneDAO);
      }

      return MilestoneMapper.toDTO(updatingMilestoneDAO);

    } catch (Exception e) {
      logger.severe("Error when updating milestone: " + e.getMessage());
      return null;
    }
  }
}