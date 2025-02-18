package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.dao.MilestoneLogDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import idatt2106.systemutvikling.sparesti.mapper.MilestoneMapper;
import idatt2106.systemutvikling.sparesti.repository.MilestoneLogRepository;
import idatt2106.systemutvikling.sparesti.repository.MilestoneRepository;
import idatt2106.systemutvikling.sparesti.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service class for handling MilestoneLogDAOs.
 */
@Service
public class MilestoneLogService {

  private final JWTService jwtService;

  private final MilestoneRepository milestoneRepository;

  private final UserRepository userRepository;

  private final MilestoneLogRepository milestoneLogRepository;

  Logger logger = Logger.getLogger(MilestoneLogService.class.getName());

  @Autowired
  public MilestoneLogService(MilestoneRepository milestoneRepository, JWTService jwtService,
      UserRepository userRepository, MilestoneLogRepository milestoneLogRepository) {
    this.milestoneRepository = milestoneRepository;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.milestoneLogRepository = milestoneLogRepository;
  }

  /**
   * Method to complete a milestone and save it to database.
   *
   * @param milestoneLogDAO the milestone log to save
   */
  public void completeMilestone(MilestoneLogDAO milestoneLogDAO) {
    milestoneLogRepository.save(milestoneLogDAO);
  }


  /**
   * Method to get all milestones for a user.
   *
   * @param username The username of the user to get milestones for.
   * @return List of MilestoneLogDAOs.
   */
  public Page<MilestoneDTO> getMilestoneLogsByUsernamePaginated(String username,
      Pageable pageable) {
    try {
      Pageable sortedPageable = PageRequest.of(
          pageable.getPageNumber(),
          pageable.getPageSize(),
          Sort.by("completionDate").descending());

      Page<MilestoneLogDAO> milestoneLogDAOs =
          milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(username, sortedPageable);

      if (milestoneLogDAOs == null) {
        return Page.empty();
      }

      List<MilestoneDTO> milestoneDTOS = new ArrayList<>();
      for (MilestoneLogDAO milestoneDAO : milestoneLogDAOs) {
        milestoneDTOS.add(MilestoneMapper.DAOLogToDTO(milestoneDAO));
      }

      return new PageImpl<>(milestoneDTOS, pageable, milestoneLogDAOs.getTotalElements());
    } catch (Exception e) {
      logger.severe("Error when getting milestones: " + e.getMessage());
      return Page.empty(); // Return an empty Page object if an exception is caught
    }
  }

  /**
   * Method to get all milestones for a user.
   *
   * @param username The username of the user to get milestones for.
   * @return List of MilestoneLogDAOs.
   */
  public List<MilestoneDTO> getMilestoneLogsByUsername(String username) {
    try {
      List<MilestoneLogDAO> milestoneLogDAOs =
          milestoneLogRepository.findMilestoneLogDAOByUserDAO_Username(username);

      List<MilestoneDTO> milestoneDTOS = new ArrayList<>();
      for (MilestoneLogDAO milestoneDAO : milestoneLogDAOs) {
        milestoneDTOS.add(MilestoneMapper.DAOLogToDTO(milestoneDAO));
      }

      return milestoneDTOS;
    } catch (Exception e) {
      logger.severe("Error when getting milestones: " + e.getMessage());
      return null;
    }
  }

  /**
   * Method to get a milestone by id.
   *
   * @param id The id of the milestone to get.
   * @return The MilestoneLogDAO.
   */
  public MilestoneDTO getMilestoneLogById(long id) {
    try {
      return MilestoneMapper.DAOLogToDTO(
          milestoneLogRepository.findMilestoneLogDAOByMilestoneId(id));
    } catch (Exception e) {
      logger.severe("Error when getting milestone: " + e.getMessage());
      return null;
    }
  }

}
