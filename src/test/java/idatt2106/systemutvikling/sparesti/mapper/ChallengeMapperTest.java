package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChallengeMapperTest {

  @Test
  @DisplayName("Test mapping from ChallengeDTO to ChallengeDAO")
  public void testToDAOMapping() {
    // Given
    ChallengeDTO dto = new ChallengeDTO();
    dto.setChallengeId(1L);
    dto.setUsername("testUser");
    dto.setChallengeTitle("Test Challenge");
    dto.setChallengeDescription("Description");
    dto.setGoalSum(100L);
    dto.setCurrentSum(50L);
    dto.setStartDate(LocalDateTime.now());
    dto.setExpirationDate(LocalDateTime.now().plusDays(7));
    dto.setRecurring(24 * 60 * 60); // DAILY
    dto.setActive(true);

    // When
    ChallengeDAO dao = ChallengeMapper.toDAO(dto);

    // Then
    assertEquals(dto.getChallengeId(), dao.getChallengeId());
    assertEquals(dto.getUsername(), dao.getUserDAO().getUsername());
    assertEquals(dto.getChallengeTitle(), dao.getChallengeTitle());
    assertEquals(dto.getChallengeDescription(), dao.getChallengeDescription());
    assertEquals(dto.getGoalSum(), dao.getGoalSum());
    assertEquals(dto.getCurrentSum(), dao.getCurrentSum());
    assertEquals(dto.getStartDate(), dao.getStartDate());
    assertEquals(dto.getExpirationDate(), dao.getExpirationDate());
    assertEquals(RecurringInterval.DAILY, dao.getRecurringInterval());
    assertEquals(dto.isActive(), dao.isActive());
  }

  @Test
  @DisplayName("Test mapping from ChallengeDAO to ChallengeDTO")
  public void testToDTOMapping() {
    // Given
    ChallengeDAO dao = new ChallengeDAO();
    dao.setChallengeId(1L);
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testUser");
    dao.setUserDAO(userDAO);
    dao.setChallengeTitle("Test Challenge");
    dao.setChallengeDescription("Description");
    dao.setGoalSum(100L);
    dao.setCurrentSum(50L);
    dao.setStartDate(LocalDateTime.now());
    dao.setExpirationDate(LocalDateTime.now().plusDays(7));
    dao.setRecurringInterval(RecurringInterval.DAILY);
    dao.setActive(true);

    // When
    ChallengeDTO dto = ChallengeMapper.toDTO(dao);

    // Then
    assertEquals(dao.getChallengeId(), dto.getChallengeId());
    assertEquals(dao.getUserDAO().getUsername(), dto.getUsername());
    assertEquals(dao.getChallengeTitle(), dto.getChallengeTitle());
    assertEquals(dao.getChallengeDescription(), dto.getChallengeDescription());
    assertEquals(dao.getGoalSum(), dto.getGoalSum());
    assertEquals(dao.getCurrentSum(), dto.getCurrentSum());
    assertEquals(dao.getStartDate(), dto.getStartDate());
    assertEquals(dao.getExpirationDate(), dto.getExpirationDate());
    assertEquals(dao.isActive(), dto.isActive());
    assertEquals(dao.getRecurringInterval().getSeconds(), dto.getRecurring());
  }
}
