package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;

/**
 * Mapper class for AchievementDAO and AchievementDTO
 */
public class AchievementMapper {
  /**
   * Maps AchievementDTO to AchievementDAO
   *
   * @param dto AchievementDTO to be mapped
   * @return AchievementDAO
   */
  public static AchievementDAO toDAO(AchievementDTO dto) {
    AchievementDAO dao = new AchievementDAO();
    dao.setAchievementId(dto.getAchievementId());
    dao.setAchievementTitle(dto.getAchievementTitle());
    dao.setAchievementDescription(dto.getAchievementDescription());
    dao.setBadge(Base64Mapper.toByteArray(dto.getBadge()));

    return dao;
  }

  /**
   * Maps AchievementDAO to AchievementDTO
   *
   * @param dao AchievementDAO to be mapped
   * @return AchievementDTO
   */
  public static AchievementDTO toDTO(AchievementDAO dao) {
    AchievementDTO dto = new AchievementDTO();

    dto.setAchievementId(dao.getAchievementId());
    dto.setAchievementTitle(dao.getAchievementTitle());
    dto.setAchievementDescription(dao.getAchievementDescription());
    dto.setBadge(Base64Mapper.toBase64String(dao.getBadge()));

    return dto;
  }
}
