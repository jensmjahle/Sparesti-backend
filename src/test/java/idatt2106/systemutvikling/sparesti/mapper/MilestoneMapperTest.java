package idatt2106.systemutvikling.sparesti.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import idatt2106.systemutvikling.sparesti.dao.MilestoneDAO;
import idatt2106.systemutvikling.sparesti.dao.UserDAO;
import idatt2106.systemutvikling.sparesti.dto.MilestoneDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MilestoneMapperTest {

  @Test
  @DisplayName("Test MilestoneDTO to MilestoneDAO mapping")
  public void testToDAOMapping() {
    // Create a sample MilestoneDTO object
    MilestoneDTO dto = new MilestoneDTO();
    dto.setMilestoneId(1L);
    dto.setUsername("testUser");
    dto.setMilestoneTitle("Test Milestone");
    // Set other properties as needed...

    // Map MilestoneDTO to MilestoneDAO
    MilestoneDAO dao = MilestoneMapper.toDAO(dto);

    // Assert that the mapping is correct
    assertNotNull(dao);
    assertEquals(dto.getMilestoneId(), dao.getMilestoneId());
    assertEquals(dto.getUsername(), dao.getUserDAO().getUsername());
    assertEquals(dto.getMilestoneTitle(), dao.getMilestoneTitle());
  }

  @Test
  @DisplayName("Test MilestoneDAO to MilestoneDTO mapping")
  public void testToDTOMapping() {
    // Create a sample MilestoneDAO object
    MilestoneDAO dao = new MilestoneDAO();
    dao.setMilestoneId(1L);
    dao.setUserDAO(new UserDAO());
    dao.getUserDAO().setUsername("testUser");
    dao.setMilestoneTitle("Test Milestone");
    // Set other properties as needed...

    // Map MilestoneDAO to MilestoneDTO
    MilestoneDTO dto = MilestoneMapper.toDTO(dao);

    // Assert that the mapping is correct
    assertNotNull(dto);
    assertEquals(dao.getMilestoneId(), dto.getMilestoneId());
    assertEquals(dao.getUserDAO().getUsername(), dto.getUsername());
    assertEquals(dao.getMilestoneTitle(), dto.getMilestoneTitle());
  }
}
