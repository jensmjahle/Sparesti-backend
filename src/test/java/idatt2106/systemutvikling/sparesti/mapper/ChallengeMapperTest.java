package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.ChallengeDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.ChallengeDTO;
import idatt2106.systemutvikling.sparesti.enums.RecurringInterval;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeMapperTest {

  // existing tests...

  @Test
  public void testMapRecurringIntervalDaily() {
    ChallengeDTO dto = new ChallengeDTO();
    dto.setRecurring(24 * 60 * 60); // DAILY

    ChallengeDAO dao = ChallengeMapper.toDAO(dto);

    assertEquals(RecurringInterval.DAILY, dao.getRecurringInterval());
  }

  @Test
  public void testMapRecurringIntervalWeekly() {
    ChallengeDTO dto = new ChallengeDTO();
    dto.setRecurring(60 * 60 * 24 * 7); // WEEKLY

    ChallengeDAO dao = ChallengeMapper.toDAO(dto);

    assertEquals(RecurringInterval.WEEKLY, dao.getRecurringInterval());
  }

  @Test
  public void testMapRecurringIntervalMonthly() {
    ChallengeDTO dto = new ChallengeDTO();
    dto.setRecurring(60 * 60 * 24 * 30); // MONTHLY

    ChallengeDAO dao = ChallengeMapper.toDAO(dto);

    assertEquals(RecurringInterval.MONTHLY, dao.getRecurringInterval());
  }

  @Test
  public void testMapRecurringIntervalNone() {
    ChallengeDTO dto = new ChallengeDTO();
    dto.setRecurring(0); // NONE

    ChallengeDAO dao = ChallengeMapper.toDAO(dto);

    assertEquals(RecurringInterval.NONE, dao.getRecurringInterval());
  }

  @Test
  public void testMapRecurringIntervalInvalid() {
    ChallengeDTO dto = new ChallengeDTO();
    dto.setRecurring(12345); // Invalid recurring interval

    assertThrows(IllegalArgumentException.class, () -> ChallengeMapper.toDAO(dto));
  }

  @Test
  public void testToDTOMappingRecurringIntervalDaily() {
    ChallengeDAO dao = new ChallengeDAO();
    dao.setRecurringInterval(RecurringInterval.DAILY);
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testUser");
    dao.setUserDAO(userDAO);

    ChallengeDTO dto = ChallengeMapper.toDTO(dao);

    assertEquals(24 * 60 * 60, dto.getRecurring());
  }

  @Test
  public void testToDTOMappingRecurringIntervalWeekly() {
    ChallengeDAO dao = new ChallengeDAO();
    dao.setRecurringInterval(RecurringInterval.WEEKLY);
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testUser");
    dao.setUserDAO(userDAO);

    ChallengeDTO dto = ChallengeMapper.toDTO(dao);

    assertEquals(60 * 60 * 24 * 7, dto.getRecurring());
  }

  @Test
  public void testToDTOMappingRecurringIntervalMonthly() {
    ChallengeDAO dao = new ChallengeDAO();
    dao.setRecurringInterval(RecurringInterval.MONTHLY);
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testUser");
    dao.setUserDAO(userDAO);

    ChallengeDTO dto = ChallengeMapper.toDTO(dao);

    assertEquals(60 * 60 * 24 * 30, dto.getRecurring());
  }

  @Test
  public void testToDTOMappingRecurringIntervalNone() {
    ChallengeDAO dao = new ChallengeDAO();
    dao.setRecurringInterval(RecurringInterval.NONE);
    UserDAO userDAO = new UserDAO();
    userDAO.setUsername("testUser");
    dao.setUserDAO(userDAO);

    ChallengeDTO dto = ChallengeMapper.toDTO(dao);

    assertEquals(0, dto.getRecurring());
  }

  @Test
  public void testToByteArrayWithImageData() {
    String base64String = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});

    byte[] decodedBytes = Base64Mapper.toByteArray(base64String);

    assertArrayEquals(new byte[]{1, 2, 3}, decodedBytes);
  }

  @Test
  public void testToByteArrayWithoutImageData() {
    String base64String = Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});

    byte[] decodedBytes = Base64Mapper.toByteArray(base64String);

    assertArrayEquals(new byte[]{1, 2, 3}, decodedBytes);
  }

  @Test
  public void testToByteArrayWithNull() {
    String base64String = null;

    byte[] decodedBytes = Base64Mapper.toByteArray(base64String);

    assertNull(decodedBytes);
  }
}