package idatt2106.systemutvikling.sparesti.mapper;

import idatt2106.systemutvikling.sparesti.dao.AchievementDAO;
import idatt2106.systemutvikling.sparesti.dto.AchievementDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AchievementMapperTest {

  @Test
  public void testToDAO() {
    AchievementDTO dto = new AchievementDTO();
    dto.setAchievementId(1L);
    dto.setAchievementTitle("Test Title");
    dto.setAchievementDescription("Test Description");
    String validBase64String = "R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7";
    dto.setBadge(validBase64String);

    if (Base64.getDecoder().decode(dto.getBadge()).length > 0) {
      AchievementDAO dao = AchievementMapper.toDAO(dto);

      assertEquals(dto.getAchievementId(), dao.getAchievementId());
      assertEquals(dto.getAchievementTitle(), dao.getAchievementTitle());
      assertEquals(dto.getAchievementDescription(), dao.getAchievementDescription());
      assertEquals(dto.getBadge(), Base64Mapper.toBase64String(dao.getBadge()));
    }
  }

  @Test
  public void testToDTO() {
    AchievementDAO dao = new AchievementDAO();
    dao.setAchievementId(1L);
    dao.setAchievementTitle("Test Title");
    dao.setAchievementDescription("Test Description");
    String validBase64String = "R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"; // Sample valid base64 string
    dao.setBadge(Base64.getDecoder().decode(validBase64String));

    AchievementDTO dto = AchievementMapper.toDTO(dao);

    assertEquals(dao.getAchievementId(), dto.getAchievementId());
    assertEquals(dao.getAchievementTitle(), dto.getAchievementTitle());
    assertEquals(dao.getAchievementDescription(), dto.getAchievementDescription());
    assertEquals(validBase64String, dto.getBadge()); // Use the same base64 string for comparison
  }

  @Test
  public void testToDAOList() {
    AchievementDTO dto1 = new AchievementDTO();
    dto1.setAchievementId(1L);
    AchievementDTO dto2 = new AchievementDTO();
    dto2.setAchievementId(2L);
    List<AchievementDTO> dtos = Arrays.asList(dto1, dto2);

    List<AchievementDAO> daos = AchievementMapper.toDAOList(dtos);

    assertEquals(dtos.size(), daos.size());
    for (int i = 0; i < dtos.size(); i++) {
      assertEquals(dtos.get(i).getAchievementId(), daos.get(i).getAchievementId());
    }
  }

  @Test
  public void testToDTOList() {
    AchievementDAO dao1 = new AchievementDAO();
    dao1.setAchievementId(1L);
    AchievementDAO dao2 = new AchievementDAO();
    dao2.setAchievementId(2L);
    List<AchievementDAO> daos = Arrays.asList(dao1, dao2);

    List<AchievementDTO> dtos = AchievementMapper.toDTOList(daos);

    assertEquals(daos.size(), dtos.size());
    for (int i = 0; i < daos.size(); i++) {
      assertEquals(daos.get(i).getAchievementId(), dtos.get(i).getAchievementId());
    }
  }
}