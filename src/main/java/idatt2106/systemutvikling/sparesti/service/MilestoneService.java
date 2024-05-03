package idatt2106.systemutvikling.sparesti.service;


import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.mapper.Base64Mapper;
import idatt2106.systemutvikling.sparesti.mapper.MilestoneMapper;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service class for handling Milestones.
 */
@Service
public class MilestoneService {

  private final MilestoneRepository milestoneRepository;

  private final UserRepository userRepository;

  private final Logger logger = Logger.getLogger(MilestoneService.class.getName());

  private final MilestoneLogService milestoneLogService;

  @Autowired
  public MilestoneService(MilestoneRepository milestoneRepository, UserRepository userRepository,
      MilestoneLogService milestoneLogService) {
    this.milestoneRepository = milestoneRepository;
    this.userRepository = userRepository;
    this.milestoneLogService = milestoneLogService;
  }

  /**
   * Method to get all active milestones for a user. The milestones are sorted by deadline date.
   *
   * @param username The username of the user to get milestones for.
   * @return List of MilestoneDTOs.
   */
  public Page<MilestoneDTO> getActiveMilestonesDTOsByUsernamePaginated(String username,
      Pageable pageable) {
    try {
      hasMilestoneTimeElapsed(milestoneRepository.findMilestoneDAOByUserDAO_Username(username));
      Pageable sortedPageable = PageRequest.of(
          pageable.getPageNumber(),
          pageable.getPageSize(),
          Sort.by("deadlineDate"));

      Page<MilestoneDAO> milestoneDAOs = milestoneRepository.findMilestoneDAOByUserDAO_Username(
          username, sortedPageable);

      List<MilestoneDTO> milestoneDTOS = new ArrayList<>();
      for (MilestoneDAO milestoneDAO : milestoneDAOs) {
        milestoneDTOS.add(MilestoneMapper.toDTO(milestoneDAO));
      }
      return new PageImpl<>(milestoneDTOS, pageable, milestoneDAOs.getTotalElements());
    } catch (Exception e) {
      logger.severe("Error when getting milestones: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to get all active milestones for a user.
   *
   * @param username The username of the user to get milestones for.
   * @return List of MilestoneDTOs.
   */
  public List<MilestoneDTO> getActiveMilestonesDTOsByUsername(String username) {
    try {
      hasMilestoneTimeElapsed(milestoneRepository.findMilestoneDAOByUserDAO_Username(username));
      List<MilestoneDAO> milestoneDAOs = milestoneRepository.findMilestoneDAOByUserDAO_Username(
          username);
      List<MilestoneDTO> milestoneDTOS = new ArrayList<>();
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
   * @param username    username of the user to complete the milestone
   * @param milestoneId the id of the milestone to complete
   */
  public void completeMilestone(String username, Long milestoneId) {
    try {
      MilestoneDAO milestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneIdAndUserDAO_Username(
          milestoneId, username);
      MilestoneLogDAO milestoneLogDAO = MilestoneMapper.toLogDAO(milestoneDAO);
      milestoneLogService.completeMilestone(milestoneLogDAO);
      milestoneRepository.delete(milestoneDAO);
    } catch (Exception e) {
      logger.severe("Error when completing milestone: " + e.getMessage());
    }
  }

  /**
   * Method to create a milestone.
   *
   * @param username     username of the user to create the milestone
   * @param milestoneDTO the milestone to create
   * @return the created milestone
   */
  public MilestoneDTO createMilestoneDTO(String username, MilestoneDTO milestoneDTO) {
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
   * Method to get a specific milestone by its id. If the milestone is not found, null is returned.
   *
   * @param milestoneID the id of the milestone
   * @return the milestone
   */
  public MilestoneDTO getMilestoneDTOById(Long milestoneID) {
    try {
      MilestoneDAO milestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneId(milestoneID);
      return MilestoneMapper.toDTO(milestoneDAO);
    } catch (Exception e) {
      logger.severe("Error when getting milestone: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to update a milestone. If the milestone is completed, it will be deleted. If not, it
   * will be updated. The milestone will be completed if the current sum is greater than or equal to
   * the goal sum.
   *
   * @param username     The username of the user to update the milestone for.
   * @param milestoneDTO The milestone to update.
   * @return The updated milestone.
   */
  public MilestoneDTO updateMilestoneDTO(String username, MilestoneDTO milestoneDTO) {
    try {
      MilestoneDAO updatingMilestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneId(
          milestoneDTO.getMilestoneId());
      updatingMilestoneDAO.setMilestoneCurrentSum(milestoneDTO.getMilestoneCurrentSum());

      if (updatingMilestoneDAO.getMilestoneCurrentSum()
          >= updatingMilestoneDAO.getMilestoneGoalSum()) {
        completeMilestone(username, updatingMilestoneDAO.getMilestoneId());
      } else {
        milestoneRepository.save(updatingMilestoneDAO);
      }

      return MilestoneMapper.toDTO(updatingMilestoneDAO);

    } catch (Exception e) {
      logger.severe("Error when updating milestone: " + e.getMessage());
      return null;
    }
  }

  public void deleteMilestone(String username, Long milestoneId) {
    try {
      MilestoneDAO milestoneDAO = milestoneRepository.findMilestoneDAOByMilestoneId(milestoneId);
      if (!Objects.equals(milestoneDAO.getUserDAO().getUsername(), username)) {
        return;
      }
      milestoneRepository.delete(milestoneDAO);
    } catch (RuntimeException e) {
      logger.severe("Error when deleting milestone: " + e.getMessage());
    }
  }


  /**
   * Method to delete a milestone. If the milestone is not found, null is returned.
   *
   * @param username     The username of the user to delete the milestone for.
   * @param milestoneDTO The milestone to delete.
   * @return True if the milestone was deleted, false otherwise.
   */

  public MilestoneDTO editMilestone(String username, MilestoneDTO milestoneDTO) {
    try {
      MilestoneDAO updatedMilestone = milestoneRepository.findMilestoneDAOByMilestoneId(
          milestoneDTO.getMilestoneId());
      UserDAO user = userRepository.findByUsername(username);
      if (!Objects.equals(user.getUsername(), updatedMilestone.getUserDAO().getUsername())) {
        return null;
      }
      if (Objects.nonNull(milestoneDTO.getMilestoneTitle())) {
        updatedMilestone.setMilestoneTitle(milestoneDTO.getMilestoneTitle());
      }
      if (Objects.nonNull(milestoneDTO.getMilestoneDescription())) {
        updatedMilestone.setMilestoneDescription(milestoneDTO.getMilestoneDescription());
      }
      if (Objects.nonNull(milestoneDTO.getMilestoneImage())) {
        updatedMilestone.setMilestoneImage(
            Base64Mapper.toByteArray(milestoneDTO.getMilestoneImage()));
      }
      if (Objects.nonNull(milestoneDTO.getMilestoneCurrentSum())) {
        updatedMilestone.setMilestoneCurrentSum(milestoneDTO.getMilestoneCurrentSum());
      }
      if (Objects.nonNull(milestoneDTO.getMilestoneGoalSum())) {
        updatedMilestone.setMilestoneGoalSum(milestoneDTO.getMilestoneGoalSum());
      }
      if (Objects.nonNull(milestoneDTO.getStartDate())) {
        updatedMilestone.setStartDate(milestoneDTO.getStartDate());
      }
      if (Objects.nonNull(milestoneDTO.getDeadlineDate())) {
        updatedMilestone.setDeadlineDate(milestoneDTO.getDeadlineDate());
      }
      milestoneRepository.save(updatedMilestone);
      return MilestoneMapper.toDTO(updatedMilestone);
    } catch (RuntimeException e) {
      logger.severe("Error when editing milestone: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to increase the current sum of a milestone. If the milestone is completed, it will be
   * deleted. If not, it will be updated. The milestone will be completed if the current sum is
   * greater than or equal to the goal sum.
   *
   * @param milestoneId The id of the milestone to increase the current sum for.
   * @param amount      The amount to increase the current sum with.
   * @return The updated milestone.
   */
  public MilestoneDAO increaseMilestonesCurrentSum(Long milestoneId, Long amount) {
    MilestoneDAO milestone = milestoneRepository.findMilestoneDAOByMilestoneId(milestoneId);

    if (milestone == null) {
      return null;
    }

    milestone.setMilestoneCurrentSum(milestone.getMilestoneCurrentSum() + amount);
    return milestoneRepository.save(milestone);
  }

  /**
   * Method to decrease the current sum of a milestone. If the milestone is completed, it will be
   * deleted. If not, it will be updated.
   *
   * @param milestoneId the id of the milestone to decrease the current sum for
   * @param amount      the amount to decrease the current sum with
   * @return the updated milestone
   */
  public MilestoneDAO decreaseMilestonesCurrentSum(Long milestoneId, Long amount) {
    return increaseMilestonesCurrentSum(milestoneId, -amount);
  }

  /**
   * Method to check if the deadline date of a milestone has elapsed. If the deadline date has
   * elapsed, the milestone will be completed.
   *
   * @param milestoneDAOS the milestones to check
   */
  public void hasMilestoneTimeElapsed(List<MilestoneDAO> milestoneDAOS) {
    LocalDateTime now = LocalDateTime.now();
    for (MilestoneDAO milestoneDAO : milestoneDAOS) {
      if (!milestoneDAO.getDeadlineDate().isAfter(now)) {
        Long milestoneId = milestoneDAO.getMilestoneId();
        String username = milestoneDAO.getUserDAO().getUsername();
        completeMilestone(username, milestoneId);
      }
    }
  }

  public void moveExpiredMilestonesToLog() {
    List<MilestoneDAO> milestones = milestoneRepository.findAll();
    hasMilestoneTimeElapsed(milestones);
  }
}
