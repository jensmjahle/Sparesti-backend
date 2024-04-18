package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;

/**
 * Mapper class for MilestoneDAO and MilestoneDTO
 */
public class MilestoneMapper {
/**
 * Maps MilestoneDTO to MilestoneDAO
 *
 * @param dto MilestoneDTO to be mapped
 * @return MilestoneDAO
 */
public static MilestoneDAO toDAO(MilestoneDTO dto) {
  MilestoneDAO dao = new MilestoneDAO();
  dao.setMilestoneId(dto.getMilestoneId());
  UserDAO userDAO = new UserDAO();
  userDAO.setUsername(dto.getUsername());
  dao.setUserDAO(userDAO);
  dao.setMilestoneTitle(dto.getMilestoneTitle());
  dao.setMilestoneDescription(dto.getMilestoneDescription());
  dao.setMilestoneGoalSum(dto.getMilestoneGoalSum());
  dao.setMilestoneCurrentSum(dto.getMilestoneCurrentSum());
  dao.setMilestoneImage(Base64Mapper.toByteArray(dto.getMilestoneImage()));
  dao.setDeadlineDate(dto.getDeadlineDate());
  dao.setStartDate(dto.getStartDate());

  return dao;
}

  /**
   * Maps MilestoneDAO to MilestoneDTO
   *
   * @param dao MilestoneDAO to be mapped
   * @return MilestoneDTO
   */
  public static MilestoneDTO toDTO(MilestoneDAO dao) {
  MilestoneDTO dto = new MilestoneDTO();
  dto.setMilestoneId(dao.getMilestoneId());
  dto.setUsername(dao.getUserDAO().getUsername());
  dto.setMilestoneTitle(dao.getMilestoneTitle());
  dto.setMilestoneDescription(dao.getMilestoneDescription());
  dto.setMilestoneGoalSum(dao.getMilestoneGoalSum());
  dto.setMilestoneCurrentSum(dao.getMilestoneCurrentSum());
  dto.setMilestoneImage(Base64Mapper.toBase64String(dao.getMilestoneImage()));
  dto.setDeadlineDate(dao.getDeadlineDate());
  dto.setStartDate(dao.getStartDate());

  return dto;
}

}