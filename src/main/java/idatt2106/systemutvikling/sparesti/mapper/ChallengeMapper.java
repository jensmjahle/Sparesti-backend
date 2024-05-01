package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;

/**
 * Mapper class for ChallengeDAO and ChallengeDAO
 */
public class ChallengeMapper {
  /**
   * Maps ChallengeDTO to ChallengeDAO
   *
   * @param dto challengeDTO to be mapped
   * @return DAO of challenge
   */
  public static ChallengeDAO toDAO(ChallengeDTO dto) {
    ChallengeDAO dao = new ChallengeDAO();
    dao.setChallengeId(dto.getChallengeId());
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername(dto.getUsername());
    dao.setUserDAO(userDAO);
    dao.setChallengeTitle(dto.getChallengeTitle());
    dao.setChallengeDescription(dto.getChallengeDescription());
    dao.setGoalSum(dto.getGoalSum());
    dao.setCurrentSum(dto.getCurrentSum());
    dao.setStartDate(dto.getStartDate());
    dao.setExpirationDate(dto.getExpirationDate());
    dao.setRecurringInterval(mapRecurringInterval(dto.getRecurring()));
    dao.setActive(dto.isActive());
    return dao;
  }

  /**
   * Maps RecurringInterval to int
   *
   * @param recurring RecurringInterval to be mapped
   * @return int
   */
  private static RecurringInterval mapRecurringInterval(int recurring) {
    return switch (recurring) {
      case 0 -> RecurringInterval.NONE;
      case 24*60*60 -> RecurringInterval.DAILY;
      case 60*60*24*7 -> RecurringInterval.WEEKLY;
      case 60*60*24*30 -> RecurringInterval.MONTHLY;
      default ->
          throw new IllegalArgumentException("Invalid recurring interval value: " + recurring);
    };
  }
  /**
   * Maps ChallengeDTO to ChallengeDAO
   *
   * @param dao ChallengeDTO to be mapped
   * @return ChallengeDAO
   */
  public static ChallengeDTO toDTO(ChallengeDAO dao) {
    ChallengeDTO dto = new ChallengeDTO();
    dto.setChallengeId(dao.getChallengeId());
    dto.setUsername(dao.getUserDAO().getUsername());
    dto.setChallengeTitle(dao.getChallengeTitle());
    dto.setChallengeDescription(dao.getChallengeDescription());
    dto.setGoalSum(dao.getGoalSum());
    dto.setCurrentSum(dao.getCurrentSum());
    dto.setStartDate(dao.getStartDate());
    dto.setExpirationDate(dao.getExpirationDate());
    dto.setActive(dao.isActive());
    dto.setRecurring(dao.getRecurringInterval().getSeconds());
    return dto;
  }

}
